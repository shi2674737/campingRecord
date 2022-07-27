<template>
    <div id="addressList">
        <div class="nav">
            <el-button class="button" @click="createCampingAddress" type="text">新增露营地点</el-button>
            <div class="nav-mid"></div>
<!--            <el-input class="search" @keyup.enter.native="getAddressName" v-model="filterForm.cartoonName" size="small" suffix-icon="el-icon-search" placeholder="请输入要搜索的番名">-->
<!--            </el-input>-->
        </div>
        <div>
            <el-table
                    :data="tableData">
                <el-table-column
                        min-width="20%"
                        align="center"
                        prop="name"
                        label="露营地名称">
                </el-table-column>
                <el-table-column
                        min-width="40%"
                        align="center"
                        prop="detailAddress"
                        label="详细地址">
                </el-table-column>
                <el-table-column
                        min-width="20%"
                        align="center"
                        prop="cost"
                        label="收费标准">
                </el-table-column>
                <el-table-column
                        min-width="20%"
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
                      <el-button
                          type="text"
                          size="small"
                          icon="el-icon-more"
                          @click="showDetail(scope.row.id)"
                      >查看</el-button>
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
            <el-form :rules="rules" :model="addressData" ref="addressData" label-width="120px">
                <el-form-item label="露营地名称" prop="name">
                  <el-input class="input" v-model="addressData.name" maxlength="50" placeholder="请输入露营地名称" show-word-limit></el-input>
                </el-form-item>
              <el-form-item label="详细地址" prop="detailAddress">
                <el-input class="input" v-model="addressData.detailAddress" maxlength="200" placeholder="请输入详细地址" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="收费标准" prop="cost">
                <el-input class="input" v-model="addressData.cost" maxlength="50" placeholder="请输入收费标准" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="phone">
                <el-input class="input" v-model="addressData.phone" maxlength="100" placeholder="请输入联系电话" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input class="input" v-model="addressData.remark" maxlength="200" placeholder="请输入备注" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="关于卡式炉" prop="sycls">
                <el-input class="input" v-model="addressData.sycls" maxlength="30"></el-input>
              </el-form-item>
              <el-form-item label="关于用碳" prop="carbon">
                <el-input class="input" v-model="addressData.carbon" maxlength="30"></el-input>
              </el-form-item>
              <el-form-item label="关于过夜" prop="overnight">
                <el-input class="input" v-model="addressData.overnight" maxlength="30"></el-input>
              </el-form-item>
              <el-form-item label="优点" prop="advantage">
                <el-input class="input" v-model="addressData.advantage" maxlength="100" placeholder="请输入优点" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="缺点" prop="disadvantage">
                <el-input class="input" v-model="addressData.disadvantage" maxlength="100" placeholder="请输入缺点" show-word-limit></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="submitForm()"
        >确 定</el-button
        >
      </span>
        </el-dialog>

        <!-- 详情 -->
        <el-dialog title="露营记录详情" :visible.sync="showDetailFlag">
            <div>
                <h4>露营地名称：{{addressDetail.name}}</h4>
            </div>
            <div>
                <h4>详细地址：{{addressDetail.detailAddress}}</h4>
            </div>
            <div>
                <h4>收费标准：{{addressDetail.cost}}</h4>
            </div>
            <div>
                <h4>联系电话：{{addressDetail.phone}}</h4>
            </div>
            <div>
                <h4>备注：{{addressDetail.remark}}</h4>
            </div>
            <div>
                <h4>关于使用卡式炉：{{addressDetail.sycls}}</h4>
            </div>
            <div>
               <h4>关于使用碳：{{addressDetail.carbon}}</h4>
            </div>
            <div>
               <h4>关于过夜：{{addressDetail.overnight}}</h4>
            </div>
            <div>
               <h4>优点：{{addressDetail.advantage}}</h4>
            </div>
            <div>
                <h4>缺点：{{addressDetail.disadvantage}}</h4>
            </div>
            <div>
              <h4>平均分：{{addressDetail.avgScore}}</h4>
            </div>
          <el-divider></el-divider>
          <div>
            <h1>最新评价</h1>
          </div>
          <el-divider></el-divider>
          <div v-for="(item) in addressDetail.comments" :key="item.id">
            <h4>评论：{{item.comment}}</h4>
            <h4>评分：{{item.score}}</h4>
            <h4>发布时间：{{item.createTime}}</h4>
            <el-divider></el-divider>
          </div>
        </el-dialog>

    </div>
</template>

<script>
import {getAddressList,
        getAddressDetail,
        updateAddress,
        addAddress} from "@/api/address";

    export default {
        methods: {

            // 详情
            async showDetail(id) {
                const res = await getAddressDetail({"id": id});
                this.addressDetail = res.data;

                this.showDetailFlag = true;
            },
            // 修改
            async updateRow(id) {
              const res = await getAddressDetail({"id": id});
              this.addressData = res.data
              this.showCreateFlag = true
              this.isChangeStatus = true
              this.dialogTitle = "修改露营地点"
            },

            // 新建
            createCampingAddress() {
                this.showCreateFlag = true
                this.isChangeStatus = false
                this.dialogTitle = "新增露营地点"
            },
            async submitForm() {
                this.$refs['addressData'].validate( async (valid) => {
                    if (valid) {
                        let res;
                        if (this.isChangeStatus) {
                          res = await updateAddress(this.addressData);
                        } else {
                          res = await addAddress(this.addressData);
                        }
                        if (res.code == 200) {
                            this.isChangeStatus = false;
                            this.showCreateFlag = false;
                            this.resetForm();

                            this.getAddressName();
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

                this.getAddressName();
                // done();
            },
            // 重置表单对象
            resetForm() {
              let addressData = {
                id:undefined,
                name:undefined,
                detailAddress:undefined,
                cost:undefined,
                phone:undefined,
                remark:undefined,
                sycls:undefined,
                carbon:undefined,
                overnight:undefined,
                advantage:undefined,
                disadvantage:undefined,
              }
              this['addressData'] = addressData
              this.$refs['addressData'].resetFields();
            },

            // 分页
            handleCurrentChange(val) {
                this.filterForm.pageNo = val
                this.getAddressList()
            },

            async getAddressName() {
                this.filterForm.pageNo = 1
                this.getAddressList()
            },

            async getAddressList() {
                const res = await getAddressList(this.filterForm)
                this.tableData = res.data.records;
                this.total = res.data.total;
            },
        },
        activated() {
            this.getAddressName();
        },
        data() {
            return {
                // 上传图片
                maxCount:50,
                hideUploadEdit: false,
                dialogVisible: false,
                disabled:false,
                dialogImageUrl: null,

                addressDetail: {
                  comments:{}
                },
                dialogTitle:"新增露营记录",
                isChangeStatus:false,
                showCreateFlag:false,
                showDetailFlag:false,

                // 新建页面下拉菜单
                addressList:[],
                visibleStatusList:[{'code':0, 'value':'所有人可见'},
                                 {'code':5, 'value':'关联人可见'}],

                addressData:{
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
                    name: [
                        { required: true, message: '请输入露营地名称', trigger: 'blur' }
                    ]
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