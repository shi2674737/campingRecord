<template>
    <div id="app">
        <div class="background" :style ="note">
            <div class="page">
                <div class="top">
                    <el-menu
                            router
                            :default-active="$route.path"
                            class="menu"
                            mode="horizontal"
                            @select="handleSelect"
                            text-color="#fff"
                            active-text-color="#aaa">
                        <el-menu-item index="/index/addressList">露营地点</el-menu-item>
                        <el-menu-item index="/index/campingList">露营记录</el-menu-item>
                        <el-menu-item index="/index/friendList">我的朋友</el-menu-item>
                        <el-menu-item @click="logout" v-show="isLoginFlag">登出</el-menu-item>
                        <el-menu-item @click="login" v-show="!isLoginFlag">登录</el-menu-item>
                    </el-menu>
                    <div class="line"></div>
                </div>
                <div class="middle">
                    <keep-alive>
                        <router-view />
                    </keep-alive>
                </div>
            </div>
        </div>
        <!-- 新建  -->
        <el-dialog customClass="customWidth" :title="dialogTitle" :visible.sync="showLogin">
            <el-form :rules="rules" :model="user" ref="cartoonInfo" label-width="120px">
                <el-form-item label="账号" prop="account">
                    <el-input class="input" v-model="user.account" maxlength="255" placeholder="请输入账号或手机号"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="account">
                    <el-input class="input" v-model="user.password" maxlength="255" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitLogin()"
        >登录</el-button
        >
      </span>
        </el-dialog>
    </div>
</template>

<script>
    import {login} from "@/api/user";

    export default {
        data() {
            return {
                activeMenu: this.$route.path,
                showLogin:false,
                dialogTitle:'登录',
                isLoginFlag:false,
                note: {
                    backgroundImage:'url(' + require('../assets/777d58c0gy1g2m6c4l7a8j21gs0mi0yo.jpg') + ')',
                    backgroundRepeat: "no-repeat center top",
                },
                user:{
                    account:"",
                    password:""
                }
            };
        },

        created() {
            this.checkLoginFlag();
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            // 登出
            logout() {
                localStorage.removeItem('access_token')
                this.isLoginFlag = false
            },
            // 登录
            login() {
                this.showLogin = true
            },
            // 提交登录
            async submitLogin() {
                const res = await login(this.user);
                window.localStorage.setItem('access_token', res.data)
                this.showLogin = false
                this.isLoginFlag = true
                location.reload()
            },
            checkLoginFlag() {
              this.isLoginFlag = localStorage.getItem('access_token') != null
            }
        }
    }
</script>

<style>
    .customWidth {
        width: 15%;
    }
    .menu {
        background: linear-gradient(to bottom,#00bfff 1%,#1e90ff 60%,#6495ed);
    }
    .middle {
        box-shadow: 1px -5px 10px #aaa, -1px -5px 10px #aaa;
    }
    .page {
        width: 62rem;
        margin: 0 auto;
    }
    @media only screen and (min-width: 0em) and (max-width: 29.9999em) {
        .page {
            max-width: 29.99em;
        }
        .customWidth {
            width: 100%;
        }
    }
    .background {
        background: no-repeat center top;
    }
</style>