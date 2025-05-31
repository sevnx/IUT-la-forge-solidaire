import api from '@/lib/api';
import { okAsync, ResultAsync } from 'neverthrow';

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

export const getTools = (): ResultAsync<Tool[], GetToolError> => {
  return ResultAsync.fromPromise(api.get('/tools'), () => {
    return GetToolError.UnexpectedError;
  }).andThen((tools) => {
    return okAsync(tools.data.data);
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
  }).andThen((tools) => {
    return okAsync(tools.data.data);
  });
};
