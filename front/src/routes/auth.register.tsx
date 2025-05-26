import { register, RegisterError } from '@/api/auth/register';
import { Button } from '@/components/ui/button';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { arktypeResolver } from '@hookform/resolvers/arktype';
import { createFileRoute, Link } from '@tanstack/react-router';
import { type } from 'arktype';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';

export const Route = createFileRoute('/auth/register')({
  component: RouteComponent,
});

const password = type.string
  .atLeastLength(8)
  .atMostLength(32)
  .configure({
    message: 'Le mot de passe doit contenir entre 8 et 32 caractères',
  })
  .narrow((data, ctx) => {
    const hasUpper = /[A-Z]/.test(data);
    const hasLower = /[a-z]/.test(data);
    const hasSymbol = /[!@#$%^&*()_+\-=\\[\]{};':"\\|,.<>/?]/.test(data);
    if (!hasUpper) {
      ctx.reject({
        message: 'Le mot de passe doit contenir au moins une majuscule',
      });
      return false;
    } else if (!hasLower) {
      ctx.reject({
        message: 'Le mot de passe doit contenir au moins une minuscule',
      });
      return false;
    } else if (!hasSymbol) {
      ctx.reject({
        message: 'Le mot de passe doit contenir au moins un caractère spécial (!@#$%^&*()_+-=[]{};:\'",.<>/?)',
      });
      return false;
    }

    return true;
  });

const Register = type({
  username: type.string.atLeastLength(2).atMostLength(32).configure({
    message: "L'identifiant doit contenir entre 2 et 32 caractères",
  }),
  address: type.string.atLeastLength(2).atMostLength(255).configure({
    message: "L'adresse doit contenir entre 2 et 255 caractères",
  }),
  password: password,
  confirmPassword: type.string,
}).narrow((data, ctx) => {
  if (data.password !== data.confirmPassword) {
    ctx.reject({
      path: ['confirmPassword'],
      message: 'Les mots de passe ne correspondent pas',
    });
    return false;
  }
  return true;
});

function RouteComponent() {
  const [error, setError] = useState<string | null>(null);
  const form = useForm<typeof Register.infer>({
    resolver: arktypeResolver(Register),
    defaultValues: {
      username: '',
      address: '',
      password: '',
      confirmPassword: '',
    },
    mode: 'onChange',
  });

  const onSubmit = async (data: typeof Register.infer) => {
    const result = await register(data);
    if (result.isErr()) {
      switch (result.error) {
        case RegisterError.UsernameAlreadyExists:
          form.setError('username', {
            message: 'Cet identifiant est déjà utilisé',
          });
          break;
        case RegisterError.UnexpectedError:
          setError('Une erreur inattendue est survenue');
          break;
      }
    }
  };

  const watch = form.watch();
  useEffect(() => {
    setError(null);
  }, [watch]);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem className="mt-6">
              <FormLabel>Identifiant</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="address"
          render={({ field }) => (
            <FormItem className="mt-6">
              <FormLabel>Adresse</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem className="mt-6">
              <FormLabel>Mot de passe</FormLabel>
              <FormControl>
                <Input {...field} type="password" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="confirmPassword"
          render={({ field }) => (
            <FormItem className="mt-6">
              <FormLabel>Confirmation du mot de passe</FormLabel>
              <FormControl>
                <Input {...field} type="password" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" className="w-full mt-4" disabled={form.formState.isSubmitting}>
          Register
        </Button>
        {error && <p className="font-medium text-sm text-red-500 mt-2">{error}</p>}
        <p className="text-sm text-muted-foreground mt-8">
          Vous avez déjà un compte ?
          <Link to="/auth/login" className="text-red-500 font-medium ml-1">
            Se connecter
          </Link>
        </p>
      </form>
    </Form>
  );
}
