import { useAuthStore } from '@/stores/auth';


export const fetchWrapper = {
  get: request('GET'),
  post: request('POST'),
  put: request('PUT'),
  delete: request('DELETE')
};

interface temp {
  method: string;
  headers: Record<string, string>;
  body?: string;
}

interface UserData {
  username: string;
  password: string;
}

function request(method: string) {
  return (url: string, body?: object) => {
    const requestOptions: temp = {
      method,
      headers: authHeader(url)
    };
    if (body) {
      requestOptions.headers['Content-Type'] = 'application/json';
      requestOptions.body = JSON.stringify(body);
    }
    return fetch(url, requestOptions).then(handleResponse);
  };
}

// helper functions

function authHeader(url: string): Record<string, string> {
  // return auth header with jwt if user is logged in and request is to the api url
  const { user } = useAuthStore();
  const isLoggedIn = !!user?.token;
  const isApiUrl = url.startsWith(import.meta.env.VITE_API_URL);
  if (isLoggedIn && isApiUrl) {
    return { Authorization: `Bearer ${user.token}` };
  } else {
    return {};
  }
}

function handleResponse(response: Response): Promise<{ status: number; data: any }> {
  return response.text().then((text: string) => {
    const data = text && JSON.parse(text);

    if (!response.ok) {
      const { user, logout } = useAuthStore();
      if ([401, 403].includes(response.status) && user) {
        // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
        logout();
      }

      const error: string = (data && data.message) || response.statusText;
      return Promise.reject({ status: response.status, error });
    }

    // Ensure data is of type UserData
    return { status: response.status, data };
  });
}

/**
 * 即使你已经有了真实的后端，fetch-wrapper.ts 文件中的代码仍然非常有用。它不仅简化了与后端的交互，还提供了重要的安全性和错误处理功能。你可以根据实际情况调整其中的配置（如 API URL 和认证逻辑），以确保其与你的后端无缝集成。
 */
