import GuestNav from '@/components/navbars/GuestNav';
import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/')({
  component: Index,
});

function Index() {
  return <GuestNav />;
}
