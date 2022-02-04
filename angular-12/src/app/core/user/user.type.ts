export interface LoginForm{
  login: string;
  password: string;
}

export interface LoginResult{
  token: string;
  expireAt: number;
}

export interface User{
  iduser: number;
  name: string;
  firstname: string;
  login: string;
  role: string;
}
