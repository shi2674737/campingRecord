<template>
    <div id="friendList">
        <div class="nav">
            <el-button class="button" @click="addUser" type="text">新增用户</el-button>
            <el-button class="button" @click="addUserRelation" type="text">添加朋友</el-button>
            <div class="nav-mid"></div>
<!--            <el-input class="search" @keyup.enter.native="getFriendName" v-model="filterForm.cartoonName" size="small" suffix-icon="el-icon-search" placeholder="请输入要搜索的番名">-->
<!--            </el-input>-->
        </div>
        <div>
            <el-table
                    :data="tableData">
                <el-table-column
                        min-width="20%"
                        align="center"
                        prop="account"
                        label="账号">
                </el-table-column>
                <el-table-column
                        min-width="20%"
                        align="center"
                        prop="realName"
                        label="真实姓名">
                </el-table-column>
                <el-table-column
                        min-width="20%"
                        align="center"
                        prop="username"
                        label="昵称">
                </el-table-column>
                <el-table-column
                        min-width="40%"
                        align="center"
                        prop="operation"
                        label="操作">
                    <template slot-scope="scope">
                    <el-button
                            type="text"
                            size="small"
                            icon="el-icon-edit"
                            @click="updateRow(scope.row.id)"
                    >修改</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 分页  -->
        <div class="pagination">
            <el-pagination
                    @background=true
                    @current-change="handleCurrentChange"
                    :current-page.sync="filterForm.pageNo"
                    :page-size="filterForm.pageSize"
                    layout="prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <!-- 新建  -->
        <el-dialog :title="dialogTitle" :before-close="cancel" :close-on-click-modal=false :visible.sync="showCreateFlag">
            <el-form :rules="rules" :model="userData" ref="userData" label-width="120px">
              <el-form-item label="账号" prop="account">
                <el-input class="input" v-model="userData.account" maxlength="20" placeholder="请输入登录账号" show-word-limit></el-input>
              </el-form-item>
              <el-form-item v-show="!isChangeStatus" label="密码" prop="password">
                <el-input class="input" v-model="userData.password" maxlength="20" placeholder="请输入密码" show-word-limit></el-input>
              </el-form-item>
              <el-form-item v-show="isChangeStatus" label="原密码" prop="oldPassword">
                <el-input class="input" v-model="userData.oldPassword" maxlength="20" placeholder="请输入确认原密码" show-word-limit></el-input>
              </el-form-item>
              <el-form-item v-show="isChangeStatus" label="新密码" prop="newPassword">
                <el-input class="input" v-model="userData.newPassword" maxlength="20" placeholder="请输入确认新密码" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="password2">
                <el-input class="input" v-model="userData.password2" maxlength="20" placeholder="请输入确认密码" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="邮箱" prop="mail">
                <el-input class="input" v-model="userData.mail" maxlength="50" placeholder="请输入邮箱" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="真实姓名" prop="realName">
                <el-input class="input" v-model="userData.realName" maxlength="20" placeholder="请输入真实姓名" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="昵称" prop="username">
                <el-input class="input" v-model="userData.username" maxlength="20" placeholder="请输入昵称" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                <el-input class="input" v-model="userData.phone" maxlength="20" placeholder="请输入手机号" show-word-limit></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="submitForm()"
        >确 定</el-button
        >
      </span>
        </el-dialog>
      <!-- 添加朋友 -->
      <el-dialog :title="dialogTitle" :before-close="cancelUserRelation" :close-on-click-modal=false :visible.sync="showUserRelationFlag">
          <el-input class="input" v-model="phone" maxlength="20" placeholder="请输入对方手机号" show-word-limit></el-input>
          <el-button type="primary" @click="getUserByPhone()">搜 索</el-button>
          <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="6">
          <el-descriptions-item v-show="userByPhone.realName" label="用户名">{{userByPhone.realName}}</el-descriptions-item>
          </el-col>
          <el-col :span="6">
          <el-button v-show="userByPhone.realName" type="primary" @click="addFriend()">添加好友</el-button>
          </el-col>
        </el-row>
      </el-dialog>
    </div>
</template>

<script>
import {
  addFriend,
  addUser,
  getFriendPage, getUserByPhone,
  getUserDetail,
  updateUser
} from "@/api/user";

    export default {
        methods: {

            // 修改
            async updateRow(id) {
              const res = await getUserDetail(id);
              this.userData = res.data
              this.showCreateFlag = true
              this.isChangeStatus = true
              this.dialogTitle = "修改用户信息"
            },

            // 新建
            addUser() {
                this.showCreateFlag = true
                this.isChangeStatus = false
                this.dialogTitle = "添加用户"
            },

            // 添加朋友
            addUserRelation() {
              this.showUserRelationFlag = true
              this.dialogTitle = "添加朋友"
            },
            async submitForm() {
                this.$refs['userData'].validate( async (valid) => {
                    if (valid) {
                        let res;
                        if (this.isChangeStatus) {
                          res = await updateUser(this.userData);
                        } else {
                          res = await addUser(this.userData);
                        }
                        if (res.code == 200) {
                            this.isChangeStatus = false;
                            this.showCreateFlag = false;
                            this.resetForm();
                            this.getFriendName();
                            return true;
                        } else {
                            alert("error submit!!");
                            console.log(res);
                        }
                    } else {
                        return false;
                    }
                });
            },
            cancel() {
                this.isChangeStatus = false;
                this.showCreateFlag = false;
                this.resetForm();
                this.getFriendName();
                // done();
            },
            cancelUserRelation() {
              this.showUserRelationFlag = false;
              this.phone = "";
              this.userByPhone = {};
            },
            async getUserByPhone() {
              if (this.phone === "") {
                return;
              }
              const res = await getUserByPhone(this.phone);
              this.userByPhone = res.data
            },
            async addFriend() {
              await addFriend(this.userByPhone.id);
              this.showUserRelationFlag = false;
              this.phone = "";
              this.userByPhone = {};
              await this.getFriendName();
            },
            // 重置表单对象
            resetForm() {
              let userData2 = {
                    id:undefined,
                    account:undefined,
                    password:undefined,
                    password2:undefined,
                    oldPassword:undefined,
                    newPassword:undefined,
                    mail:undefined,
                    realName:undefined,
                    username:undefined,
                    phone:undefined,
                  }
              this['userData'] = userData2
              this.$refs['userData'].resetFields();
            },

            // 分页
            handleCurrentChange(val) {
                this.filterForm.pageNo = val
                this.getFriendPage()
            },

            async getFriendName() {
                this.filterForm.pageNo = 1
                this.getFriendPage()
            },

            async getFriendPage() {
                const res = await getFriendPage(this.filterForm)
                this.tableData = res.data.records;
                this.total = res.data.total;
            },
        },
        activated() {
            this.getFriendName();
        },
        data() {
          let validatePassword = (rule, value, callback) => {
            if (!this.isChangeStatus) {
              if (value === '') {
                callback(new Error('请输入密码'));
              }
            }
            callback();
          };
          let validatePassword2 = (rule, value, callback) => {
            if (!this.isChangeStatus) {
              if (value === '') {
                callback(new Error('请再次输入密码'));
              } else if (value !== this.userData.password && value !== this.userData.password) {
                callback(new Error('两次输入密码不一致!'));
              }
            }
            callback();
          };
          let validateOldPassword = (rule, value, callback) => {
            if (this.isChangeStatus) {
              if (value === '' && this.userData.newPassword !== '') {
                callback(new Error('请输入原密码'));
              }
            }
            callback();
          };
          let validateNewPassword = (rule, value, callback) => {
            if (this.isChangeStatus) {
              if (value === '' && this.userData.oldPassword !== '') {
                callback(new Error('请输入新密码'));
              }
            }
            callback();
          };
          return {
                addressDetail: {
                },
                dialogTitle:"添加用户",
                isChangeStatus:false,
                showCreateFlag:false,
                showUserRelationFlag:false,
                showDetailFlag:false,

                phone:"",
                userByPhone:{},

                // 新建页面下拉菜单
                addressList:[],
                visibleStatusList:[{'code':0, 'value':'所有人可见'},
                                 {'code':5, 'value':'关联人可见'}],
                userData:{
                },

                dateLabelName:"选择日期",
                filterForm: {
                    campingName: "",
                    pageSize: 10,
                    pageNo: 1
                },
                tableData: [],
                total: 0,

                // 评分的颜色
                colors: ['#99A9BF', '#F7BA2A', '#FF9900'],

                // 校验规则
                rules: {
                    account: [
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ],
                    password: [
                        { validator: validatePassword, trigger: 'blur' }
                    ],
                    password2: [
                        { validator: validatePassword2, trigger: 'blur' }
                    ],
                    oldPassword: [
                        { validator: validateOldPassword, trigger: 'blur' }
                    ],
                    newPassword: [
                        { validator: validateNewPassword, trigger: 'blur' }
                    ],
                    mail: [
                        { required: true, message: '请输入邮箱地址', trigger: 'blur' }
                    ],
                    realName: [
                        { required: true, message: '请输入真实姓名', trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '请输入昵称', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '请输入手机号', trigger: 'blur' }
                    ],
                },
            }
        }
    }
</script>

<style>
    .el-table .warning-row {
        background: oldlace;
    }
    .el-table .success-row {
        background: #f0f9eb;
    }
    .pagination {
        background: linear-gradient(to bottom,#00bfff 1%,#1e90ff 60%,#6495ed);
    }
    .search {
        padding:  0.3em 0.3em 0.3em 0.3em;
        flex: 2;
        justify-content:flex-end;
        display: flex;
    }
    .nav {
        display: flex;
        background: #f2f6fc;
    }
    .upload {
        display: flex;
        flex: 1;
    }
    @media screen and (max-width: 500px) {
        .upload {
            display: none;
        }
    }
    .button {
        flex: 1;
        justify-content:flex-start;
    }
    .nav-mid {
        flex: 3;
    }
    .input {
        width: 200px;
    }
    .text {
        width: 500px;
    }
    @media screen and (max-width: 500px) {
        .text {
            width: 200px;
        }
    }
    .table {
        display: flex;
    }
    .timeline {
         margin: 14px;
     }
    @media screen and (max-width: 500px) {
        .el-dialog {
            width: 100%;
        }
    }
    .el-dialog__body {
        padding: 0px 20px 20px 20px;
    }
</style>