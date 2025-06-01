import api from '@/lib/api';
import axios from 'axios';
import { okAsync, ResultAsync } from 'neverthrow';

export interface BorrowDescription {
  returnDate: string;
}

export enum CreateToolBorrowError {
  AlreadyBorrowed,
  NotFound,
  UnexpectedError,
}

export const createToolBorrow = (
  toolId: number,
  borrowDescription: BorrowDescription,
): ResultAsync<void, CreateToolBorrowError> => {
  return ResultAsync.fromPromise(api.post(`/tools/${toolId}/borrow-requests`, borrowDescription), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 404) {
        return CreateToolBorrowError.NotFound;
      }
      if (error.response?.status === 409) {
        return CreateToolBorrowError.AlreadyBorrowed;
      }
    }
    return CreateToolBorrowError.UnexpectedError;
  });
};

export interface ToolBorrowRequest {
  id: number;
  username: string;
  requestDate: string;
  returnDate: string;
}

export enum GetToolBorrowRequestsError {
  UnexpectedError = 'Unexpected error',
  NotFound = 'Not found',
}

export const getToolBorrowRequests = (toolId: number): ResultAsync<ToolBorrowRequest[], GetToolBorrowRequestsError> => {
  return ResultAsync.fromPromise(api.get(`/tools/${toolId}/borrow-requests`), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 404) {
        return GetToolBorrowRequestsError.NotFound;
      }
    }
    return GetToolBorrowRequestsError.UnexpectedError;
  }).andThen((response) => {
    return okAsync(response.data.data);
  });
};

export type ToolBorrowRequestDecision = 'ACCEPTED' | 'REFUSED';
export type ToolBorrowRequestStatus = 'PENDING' | ToolBorrowRequestDecision;

export enum UpdateToolBorrowRequestError {
  UnexpectedError = 'Unexpected error',
  NotFound = 'Not found',
  DecisionAlreadyTaken = 'Decision already taken',
}

export const updateToolBorrowRequest = (
  requestId: number,
  decision: ToolBorrowRequestDecision,
): ResultAsync<void, UpdateToolBorrowRequestError> => {
  return ResultAsync.fromPromise(api.put(`/borrow-requests/${requestId}`, { state: decision }), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 404) {
        return UpdateToolBorrowRequestError.NotFound;
      }
      if (error.response?.status === 409) {
        return UpdateToolBorrowRequestError.DecisionAlreadyTaken;
      }
    }
    return UpdateToolBorrowRequestError.UnexpectedError;
  });
};

export enum GetUserBorrowsError {
  UnexpectedError = 'Unexpected error',
}

export interface UserBorrow {
  name: string;
  description: string;
  imageSrc: string;
  dateRequest: string;
  dateReturn: string;
}

export const getUserBorrows = (): ResultAsync<UserBorrow[], GetUserBorrowsError> => {
  return ResultAsync.fromPromise(api.get('/user/borrows'), () => {
    return GetUserBorrowsError.UnexpectedError;
  }).andThen((response) => {
    return okAsync(response.data.data);
  });
};

export interface UserBorrowRequest {
  name: string;
  description: string;
  imageSrc: string;
}

export const getUserBorrowRequests = (): ResultAsync<UserBorrowRequest[], GetUserBorrowsError> => {
  return ResultAsync.fromPromise(api.get('/user/borrow-requests'), () => {
    return GetUserBorrowsError.UnexpectedError;
  }).andThen((response) => {
    return okAsync(response.data.data);
  });
};