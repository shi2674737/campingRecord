<template>
    <div id="campingList">
        <div class="nav">
            <el-button class="button" @click="createCampingRecord" type="text">新增露营记录</el-button>
            <div class="nav-mid"></div>
            <el-input class="search" @keyup.enter.native="getCampingByAddressName" v-model="filterForm.cartoonName" size="small" suffix-icon="el-icon-search" placeholder="请输入要搜索的番名">
            </el-input>
        </div>
        <div>
            <el-table
                    :data="tableData">
                <el-table-column
                        min-width="40%"
                        align="center"
                        prop="addressName"
                        label="露营地点">
                </el-table-column>
                <el-table-column
                        min-width="10%"
                        align="center"
                        prop="peopleNum"
                        label="人数">
                </el-table-column>
                <el-table-column
                        min-width="10%"
                        align="center"
                        prop="weather"
                        label="天气">
                </el-table-column>
                <el-table-column
                        min-width="10%"
                        align="center"
                        prop="score"
                        label="评分">
                </el-table-column>
                <el-table-column
                        min-width="30%"
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
                      <el-popconfirm
                          trigger="click"
                          @confirm="deleteRow(scope.row.id)"
                          title="你确定要删除吗？">
                        <el-button
                            type="text"
                            size="small"
                            v-show="scope.row.isOwn"
                            icon="el-icon-delete"
                            slot="reference"
                        >删除</el-button>
                      </el-popconfirm>
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
        <el-dialog :title="dialogTitle" :before-close="cancel" :visible.sync="showCreateFlag">
            <el-form :rules="rules" :model="campingRecord" ref="campingRecord" label-width="120px">
                <el-form-item label="露营地点" prop="addressId">
                <el-select v-model="campingRecord.addressId" clearable filterable placeholder="请选择露营地点">
                    <el-option
                            v-for="item in addressList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
                </el-form-item>
              <el-form-item label="可见权限" prop="visibleStatus">
                <el-select v-model="campingRecord.visibleStatus" clearable filterable placeholder="请选择可见权限">
                  <el-option
                      v-for="item in visibleStatusList"
                      :key="item.code"
                      :label="item.value"
                      :value="item.code">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="露营开始时间" prop="campingStartTime">
                <el-date-picker
                        v-model="campingRecord.campingStartTime"
                        align="right"
                        type="datetime"
                        value-format="yyyy-MM-dd hh:mm:ss"
                        placeholder="请选择时间"
                        :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="露营开结束时间" prop="campingEndTime">
                <el-date-picker
                    v-model="campingRecord.campingEndTime"
                    align="right"
                    type="datetime"
                    value-format="yyyy-MM-dd hh:mm:ss"
                    placeholder="请选择时间"
                    :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="参与人" prop="campingParticipant">
                <el-input class="input" v-model="campingRecord.campingParticipant" maxlength="200" placeholder="请输入参与人名称" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="花费总金额" prop="totalAmount">
                <el-input class="input" v-model="campingRecord.totalAmount" maxlength="200" placeholder="请输入数字"></el-input>
              </el-form-item>
              <el-form-item label="参与人数" prop="peopleNum">
                <el-input class="input" v-model="campingRecord.peopleNum" maxlength="200" placeholder="请输入数字"></el-input>
              </el-form-item>
              <el-form-item label="天气" prop="weather">
                <el-input class="input" v-model="campingRecord.weather" maxlength="20" placeholder="请输入文字" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input class="input" v-model="campingRecord.remark" maxlength="200" placeholder="请输入备注" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="评论" prop="comment">
                <el-input type="textarea" class="input" v-model="campingRecord.comment" maxlength="500" placeholder="请输入评论" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="评分" prop="score">
                <el-input class="input" v-model="campingRecord.score" maxlength="200" placeholder="请输入评分"></el-input>
              </el-form-item>
              <el-form-item label="上传图片" prop="images">
                <el-upload
                    action="#"
                    list-type="picture-card"
                    accept=".png,.jpg,.jpeg"
                    :file-list="fileList"
                    :before-upload="beforeUpload"
                    :limit="maxCount"
                    :on-remove="handleRemove"
                    :on-exceed="handleExceed"
                    :http-request="httpRequest"
                    :class="{ hide: hideUploadEdit }"
                >
                  <i class="el-icon-plus"/>
                </el-upload>
                <el-dialog width="800px" :visible.sync="dialogVisible" :close-on-click-modal="false" :modal="false">
                  <div style="height: 600px;"><img style="width: 100%;height: 100%;" :src="dialogImageUrl" alt=""></div>
                </el-dialog>
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
                <h4>露营地点：{{campingDetail.addressName}}</h4>
            </div>
            <div>
                <h4>可见权限：{{campingDetail.visibleStatusStr}}</h4>
            </div>
            <div>
                <h4>露营开始时间：{{campingDetail.campingStartTime}}</h4>
            </div>
            <div>
                <h4>露营结束时间：{{campingDetail.campingEndTime}}</h4>
            </div>
            <div>
                <h4>参与人：{{campingDetail.campingParticipant}}</h4>
            </div>
            <div>
                <h4>总花费：{{campingDetail.totalAmount}}</h4>
            </div>
            <div>
               <h4>参与人数：{{campingDetail.peopleNum}}</h4>
            </div>
            <div>
               <h4>天气：{{campingDetail.weather}}</h4>
            </div>
            <div>
               <h4>备注：{{campingDetail.remark}}</h4>
            </div>
            <div>
                <h4>评论：{{campingDetail.comment}}</h4>
            </div>
            <div>
               <h4>评分：{{campingDetail.score}}</h4>
            </div>
            <el-image v-for="image in campingDetail.images" :key="image.name" :src="image.url" lazy></el-image>
        </el-dialog>

    </div>
</template>

<script>
import {
  addCampingRecord,
  deleteMyCampingRecord,
  getCampingRecordDetail,
  getMyCampingRecordList, updateCampingRecord,
} from '@/api/camping'
import {getAllAddressList} from "@/api/address";
import {uploadImage} from "@/api/oss";

    export default {
        methods: {
          beforeUpload(file) {
            var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
            const extension = testmsg === 'jpg'
            const extension2 = testmsg === 'jpeg'
            const extension3 = testmsg === 'png'
            if (!extension && !extension2 && !extension3) {
              this.$message({ message: '请上传 jpg png jpeg 格式类型的图片!', type: 'warning' })
              return false
            }
          },
          handleRemove(file, fileList) {
            this.fileList = fileList
          },
          async httpRequest(file) {
            let form = new FormData();
            form.append('file', file.file);
            const res = await uploadImage(form);
            this.fileList.push({ name: res.data.path, url: res.data.url })
            // self.emitInput(self.fileList)
          },
          handleExceed() {
            this.$message({
              message: '最多只能上传' + this.maxCount + '张图片',
              type: 'warning',
              duration: 1000
            })
          },

            // 详情
            async showDetail(campingId) {
                const res = await getCampingRecordDetail({"campingId": campingId});
                this.campingDetail = res.data;

                this.showDetailFlag = true;
            },
            // 删除
            async deleteRow(campingId) {
              await deleteMyCampingRecord(campingId);

              this.filterForm.pageNo = 1
              this.getCampingList()
            },
            // 修改
            async updateRow(campingId) {
              this.getAllAddress()
              const res = await getCampingRecordDetail({"campingId": campingId});
              this.campingRecord = res.data
              this.fileList = res.data.images
              this.showCreateFlag = true
              this.isChangeStatus = true
              this.dialogTitle = "修改露营记录"
            },

            // 新建
            createCampingRecord() {
                this.getAllAddress()
                this.showCreateFlag = true
                this.isChangeStatus = false
                this.dialogTitle = "新增露营记录"
            },
            async submitForm() {
                this.$refs['campingRecord'].validate( async (valid) => {
                    if (valid) {
                        this.campingRecord.images = this.fileList;
                        let res;
                      console.log(this.isChangeStatus)
                        if (this.isChangeStatus) {
                          res = await updateCampingRecord(this.campingRecord);
                        } else {
                          res = await addCampingRecord(this.campingRecord);
                        }
                        if (res.code == 200) {
                            this.isChangeStatus = false;
                            this.showCreateFlag = false;
                            this.$refs['campingRecord'].resetFields()
                            // 重置按钮
                            this.cartoonNameRadio= false;
                            this.cartoonPartNameRadio= false;
                            // 重置校验规则
                            this.rules.name = [].concat(this.nameRule);
                            this.rules.id = [];
                            this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                            this.rules["cartoonPart.id"] = [];
                            // 重置上传控件
                            this.fileList = [];


                            if (this.isEditStatus) {
                                this.showDetail(this.campingDetail.id)
                                this.isEditStatus = false;
                            }
                            this.getCampingByAddressName();
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
                this.isEditStatus = false;
                this.showCreateFlag = false;
                this.$refs['campingRecord'].resetFields();
                this.cartoonInfo.cartoonPart.cartoonPartRelation = {}
                // 重置按钮
                this.cartoonNameRadio= false;
                this.cartoonPartNameRadio= false;
                // 重置校验规则
                this.rules.name = [].concat(this.nameRule);
                this.rules.id = [];
                this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                this.rules["cartoonPart.id"] = [];
                // 重置上传控件
                this.fileList = [];

                this.getCampingByAddressName();
                // done();
            },

            // 分页
            handleCurrentChange(val) {
                this.filterForm.pageNo = val
                this.getCampingList()
            },

            async getCampingByAddressName() {
                this.filterForm.pageNo = 1
                this.getCampingList()
            },

            async getCampingList() {
                const res = await getMyCampingRecordList(this.filterForm)
                this.tableData = res.data.records;
                this.total = res.data.total;
            },
            // 获取番剧名称select列表
            async getAllAddress() {
                const res = await getAllAddressList()
                this.addressList = res.data;
            },
            // 获取分季名称select列表
            async getAllCartoonParts() {
                // const res = await getMyCampingRecordList(cartoonId);
                // this.cartoonPartList = res.data;
            },
        },
        created() {
            this.getCampingByAddressName();
        },
        data() {
            return {
                // 上传图片
                maxCount:50,
                hideUploadEdit: false,
                dialogVisible: false,
                disabled:false,
                dialogImageUrl: null,
                fileList: [],

                campingDetail: {
                },
                dialogTitle:"新增露营记录",
                isEditStatus:false,
                isChangeStatus:false,
                showCreateFlag:false,
                showDetailFlag:false,
                cartoonNameRadio: false,
                cartoonPartNameRadio: false,
                cartoonPartDisabledFlag: true,

                // 新建页面下拉菜单
                addressList:[],
                visibleStatusList:[{'code':0, 'value':'所有人可见'},
                                 {'code':5, 'value':'关联人可见'}],

                cartoonNames: [],
                campingRecord:{
                  id:undefined,
                  addressId:undefined,
                  visibleStatus:undefined,
                  campingStartTime:undefined,
                  campingEndTime:undefined,
                  campingParticipant:undefined,
                  totalAmount:undefined,
                  peopleNum:undefined,
                  weather:undefined,
                  remark:undefined,
                  comment:undefined,
                  score:undefined,
                  images:[],
                  relationUserId:[],
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

                // 日期快捷选择
                pickerOptions: {
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '下个1月1',
                        onClick(picker) {
                            const now = new Date();
                            let date = new Date(now.getFullYear(), 0, 1);
                            date = now < date ? date : new Date(now.getFullYear() + 1, 0, 1);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '下个4月1',
                        onClick(picker) {
                            const now = new Date();
                            let date = new Date(now.getFullYear(), 3, 1);
                            date = now < date ? date : new Date(now.getFullYear() + 1, 3, 1);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '下个7月1',
                        onClick(picker) {
                            const now = new Date();
                            let date = new Date(now.getFullYear(), 6, 1);
                            date = now < date ? date : new Date(now.getFullYear() + 1, 6, 1);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '下个10月1',
                        onClick(picker) {
                            const now = new Date();
                            let date = new Date(now.getFullYear(), 9, 1);
                            date = now < date ? date : new Date(now.getFullYear() + 1, 9, 1);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                // 校验规则
                rules: {
                    name: [
                        { required: true, message: '请输入番剧名称', trigger: 'blur' }
                    ],
                    id: [
                        // { required: true, message: '请选择番剧名称', trigger: ["blur",'change']}
                    ],
                    'cartoonPart.name': [
                        { required: true, message: '请输入分季名称', trigger: 'blur' }
                    ],
                    'cartoonPart.id': [
                        // { required: true, message: '请输入分季名称', trigger: ["blur",'change']}
                    ],
                    'cartoonPart.cartoonPartRelation.relationStatus': [
                        { required: true, message: '请选择追番状态', trigger: 'change' }
                    ],
                    // 'cartoonPart.cartoonPartRelation.relationDate': { required: true, message: '请选择日期', trigger: 'change' }
                },
                nameRule: [{ required: true, message: '请输入番剧名称', trigger: 'blur' }],
                idRule: [{ required: true, message: '请选择番剧名称', trigger: ["blur",'change']}],
                cartoonPartNameRule: [{ required: true, message: '请输入分季名称', trigger: 'blur' }],
                cartoonPartIdRule: [{ required: true, message: '请输入分季名称', trigger: ["blur",'change']}],
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