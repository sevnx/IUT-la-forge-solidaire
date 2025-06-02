import { getUserBorrowRequests } from "@/api/tools/borrows";
import { EmptyPage } from "@/components/core/EmptyPage";
import { neverthrowToError } from "@/lib/neverthrow";
import { useQuery } from "@tanstack/react-query";
import { UserBorrowsEmpty } from "./UserBorrowsEmpty";
import { UserBorrowRequestItem } from "./UserBorrowRequestItem";

export function UserBorrowRequestItems() {
    const { data: requests } = useQuery({
        queryKey: ['user-borrows-requests'],
        queryFn: neverthrowToError(getUserBorrowRequests),
    });

    if (requests === undefined) {
        return <EmptyPage />;
    }

    if (requests.length === 0) {
        return <UserBorrowsEmpty type="requests" />;
    }

    return <div>
        {requests.map((request, i) => (
            <UserBorrowRequestItem key={i} request={request} />
        ))}
    </div>
}