import { createFileRoute, Link } from '@tanstack/react-router';
import { type } from 'arktype';
import { useForm } from 'react-hook-form';
import { arktypeResolver } from '@hookform/resolvers/arktype';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { useState } from 'react';
import { useAuth } from '@/context/AuthContext';

export const Route = createFileRoute('/auth/login')({
  component: RouteComponent,
});

const Login = type({
  username: type.string.atLeastLength(1).configure({
    message: 'L\'identifiant est requis',
  }),
  password: type.string.atLeastLength(1).configure({
    message: 'Le mot de passe est requis',
  }),
});

function RouteComponent() {
  const [message, setMessage] = useState<string | null>(null);
  const { login } = useAuth();

  const form = useForm<typeof Login.infer>({
    resolver: arktypeResolver(Login),
    defaultValues: {
      username: '',
      password: '',
    },
  });

  const onSubmit = async (data: typeof Login.infer) => {
    await login(data);
  };

  const clearMessage = () => {
    if (message) {
      setMessage(null);
    }
  };

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
                <Input {...field} onChange={(e) => { field.onChange(e); clearMessage(); }} />
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
                <Input {...field} onChange={(e) => { field.onChange(e); clearMessage(); }} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <p className="text-red-500 text-sm font-medium mt-2 min-h-[1.25rem]">{message || ''}</p>
        <Button type="submit" className="w-full mt-4" disabled={form.formState.isSubmitting}>
          Login
        </Button>
        <p className="text-sm text-muted-foreground mt-8">
          Vous n'avez pas de compte ?
          <Link to="/auth/register" className="text-red-500 font-medium ml-1">Créer un compte</Link>
        </p>
      </form>
    </Form>
  );
}
