import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { useForm } from 'react-hook-form';
import { Button } from '@/components/ui/button';
import { FileDropZone } from '@/components/ui/file-drop-zone';
import { createTool } from '@/api/tools/tools';
import { toast } from 'sonner';

export interface ToolCreateSchema {
  name: string;
  description: string;
  image: File | null;
}

export interface UserToolCreateFormProps {
  onSubmit: () => void;
}

export function UserToolCreateForm({ onSubmit }: UserToolCreateFormProps) {
  const form = useForm<ToolCreateSchema>({
    defaultValues: {
      name: '',
      description: '',
      image: null,
    },
  });

  const handleSubmit = async (data: ToolCreateSchema) => {
    let error = false;
    if (data.name.length === 0) {
      form.setError('name', { message: 'Le nom est requis' });
      error = true;
    }

    if (data.description.length === 0) {
      form.setError('description', { message: 'La description est requise' });
      error = true;
    }

    if (data.image === null) {
      form.setError('image', { message: "L'image est requise" });
      error = true;
    }

    if (error) {
      return;
    }

    const result = await createTool({
      name: data.name,
      description: data.description,
      image: data.image as File,
    });

    if (result.isErr()) {
      toast.error('Erreur lors de la création de l\'outil');
      return;
    }
    toast.success('Outil créé avec succès');
    onSubmit();
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-4">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Nom</FormLabel>
              <FormControl>
                <Input
                  {...field}
                  placeholder="Nom de l'outil"
                  onChange={(e) => {
                    field.onChange(e.target.value);
                    if (e.target.value.length === 0) {
                      form.setError('name', { message: 'Le nom est requis' });
                    } else {
                      form.clearErrors('name');
                    }
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="description"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Description</FormLabel>
              <FormControl>
                <Textarea
                  {...field}
                  rows={4}
                  placeholder="Description de l'outil"
                  onChange={(e) => {
                    field.onChange(e.target.value);
                    if (e.target.value.length === 0) {
                      form.setError('description', { message: 'La description est requise' });
                    } else {
                      form.clearErrors('description');
                    }
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="image"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Image</FormLabel>
              <FormControl>
                <FileDropZone onFileSelect={field.onChange} acceptedTypes={['image/*']} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" className="w-full">
          Créer l'outil
        </Button>
      </form>
    </Form>
  );
}
