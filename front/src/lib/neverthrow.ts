import { ResultAsync } from "neverthrow";

export const neverthrowToError = <T, E extends string>(fn: () => ResultAsync<T, E>) => {
  return async () => {
    const result = await fn();
    if (result.isErr()) {
      throw new Error(result.error.toString());
    }
    return result.value;
  };
};