import api from '@/lib/api';
import axios from 'axios';
import { ResultAsync } from 'neverthrow';

export interface Tool {
  id: number;
  name: string;
  description: string;
  imageSrc: string;
  // Time in ISO 8601 format
  availableAt?: string;
  ownerAddress?: string;
}

export enum GetToolError {
  UnexpectedError = 'Unexpected error',
}

export const getTools = (): ResultAsync<Tool[], Error> => {
  return ResultAsync.fromPromise(api.get('/tools'), (error) => {
    if (axios.isAxiosError(error)) {
      return new Error(GetToolError.UnexpectedError);
    }
    return new Error(GetToolError.UnexpectedError);
  });
};

export interface ToolDescription {
  name: string;
  description: string;
  image: File;
}

export enum CreateToolError {
  UnexpectedError = 'Unexpected error',
}

export const createTool = (toolDescription: ToolDescription): ResultAsync<void, CreateToolError> => {
  return ResultAsync.fromPromise(api.post('/tools', toolDescription), () => {
    return CreateToolError.UnexpectedError;
  });
};

export const getUserTools = (): ResultAsync<Tool[], CreateToolError> => {
  return ResultAsync.fromPromise(api.get('/user/tools'), () => {
    return CreateToolError.UnexpectedError;
  });
};
