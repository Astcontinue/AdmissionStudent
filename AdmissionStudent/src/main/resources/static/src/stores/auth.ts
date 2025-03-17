import {defineStore} from 'pinia';
import {router} from '@/router';
import {fetchWrapper} from '@/utils/helpers/fetch-wrapper';

const baseUrl = `${import.meta.env.VITE_API_URL}`;

//用于接受后端响应过来的用户信息
export interface User {
    id: number;
    username: string;
    firstname: string;  // 新增
    lastname: string;   // 新增
    photo?: string;
    role: string;
    // 删除 name 字段
}


//游客身份的信息
interface AuthState {
    user: User;
    returnUrl: string | null;
    loading: boolean;
}
//创建一个全局的状态管理store，用于处理用户的认证状态，包括登录、注销、注册以及用户信息的持久化和更新
//这是用户认证状态管理的核心代码

export const useAuthStore = defineStore({
    id: 'auth',
    // 用户状态初始化
    state: () => ({
        // initialize state from local storage to enable user to stay logged in
        /* eslint-disable-next-line @typescript-eslint/ban-ts-comment */
        // @ts-ignore
        user: {
            id: 0,
            username: ' ',       // 新增
            firstname: ' ',      // 新增
            lastname: ' ',       // 新增
            role: ' ',
            photo: ' ',
            // 兼容旧数据结构
            ...JSON.parse(localStorage.getItem('user') || '{}')
        } as User,
        returnUrl: null,
        loading: false
    }),
    actions: {

        /**
         * 用户登录的前端接口
         */
        async login(username: string, password: string) {
            try {
                //前端登录请求
                const response = await fetchWrapper.post<{
                    id: number;
                    username: string;
                    firstname: string;
                    lastname: string;
                    role?: string;
                    photo?: string;
                    token: string;
                }>(`${baseUrl}/user/login`, { username, password });

                this.user = {
                    id: response.data.id,
                    username: response.data.username,
                    firstname: response.data.firstname,
                    lastname: response.data.lastname,
                    role: response.data.role || 'guest',
                    photo: response.data.photo || ''
                };

                // 安全存储方案（仅存必要字段）
                localStorage.setItem('user', JSON.stringify(this.user));

                //路由跳转页面
                router.push(this.returnUrl || '/dashboard');
            } catch (error) {
                //登录失败处理手段
                console.error('登录失败:', error);
                throw error;
            }
        },

        /**
         * 用户登出的前端接口
         */
        logout() {
            // 修正为匹配User接口的结构
            this.user = {
                id: '',
                username: '',
                firstname: '',
                lastname: '',
                role: 'guest',
                photo: ''
            };
            localStorage.removeItem('user');
            router.push('/login');
        },

        /**
         * 注册功能的前端接口
         */
        async register(firstname: string, lastname: string, username: string, password: string, company: string) {
            try {
                // 1. 发送注册请求
                const user = await fetchWrapper.post(`${baseUrl}/account/register`, {
                    firstname,
                    lastname,
                    username,
                    password,
                    company
                });
                // 注册成功后直接登录（可选）
                // 2. 自动登录处理
                this.user = user;
                localStorage.setItem('user', JSON.stringify(user));

                // 3. 页面跳转
                router.push('/dashboard'); // 跳转到仪表盘
            } catch (error) {
                const message = error.response?.data?.message || '注册失败，请检查网络连接';
                console.error('注册错误详情:', {
                    code: error.code,
                    stack: error.stack
                });
                throw new Error(message); // 提供友好错误信息
            }
        },


        /**
         * 实时更新用户完整个人信息的核心方法
         */
        /*async fetchUserProfile() {
            // 安全校验
            if (!this.user.id) return;
            try {
                // 1. 获取最新数据
                const freshUser = await fetchWrapper.get(`${baseUrl}/user/${this.user.id}`);
                // 2. 合并更新状态
                this.user = {
                    // 保留现有状态
                    ...this.user,
                    // 更新用户名
                    name: freshUser.name,
                    // 更新角色
                    role: freshUser.role,
                    // 更新头像
                    avatar: freshUser.avatar
                        ? `${import.meta.env.VITE_RESOURCE_URL}${freshUser.avatar}`
                        : require('@/assets/images/users/avatar-1.png')
                };
            } catch (error) {
                // 3. 错误处理
                console.error('用户信息更新失败:', error);
            }
        }*/
    },
});
