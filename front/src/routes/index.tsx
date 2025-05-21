import GuestNavbar from '@/components/navbars/GuestNavbar';
import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/')({
  component: Index,
});

function Index() {
  return (
    <GuestNavbar />
  );
}
