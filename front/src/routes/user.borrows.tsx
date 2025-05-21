import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/user/borrows')({
  component: RouteComponent,
})

function RouteComponent() {
  return <div>Hello "/borrowed"!</div>
}
