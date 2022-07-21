<template>
    <div id="campingList">
        <div class="nav">
            <el-button class="button" @click="createCartoonRecord" type="text">新增露营记录</el-button>
<!--            <el-button class="button" @click="exportCartoonRecord" type="text">导出追番记录</el-button>-->
<!--            <el-upload class="upload"-->
<!--                   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"-->
<!--                   action=""-->
<!--                   :limit=1-->
<!--                   :http-request="uploadHttpRequest"-->
<!--                   :on-exceed="exceedFile"-->
<!--                   :before-upload="beforeUpload"-->
<!--                   :on-success="uploadSuccess"-->
<!--                   :on-error="uploadError">-->
<!--                <el-button class="button" type="text">导入追番记录</el-button>-->
<!--            </el-upload>-->
            <div class="nav-mid"></div>
            <el-input class="search" @keyup.enter.native="getCartoonByName" v-model="filterForm.cartoonName" size="small" suffix-icon="el-icon-search" placeholder="请输入要搜索的番名">
            </el-input>
        </div>
        <div>
            <el-table
                    :data="tableData"
                    :row-class-name="tableRowClassName">
                <el-table-column
                        min-width="40%"
                        align="center"
                        prop="name"
                        label="露营地点">
                </el-table-column>
                <el-table-column
                        min-width="17%"
                        align="center"
                        prop="peopleNum"
                        label="人数">
                </el-table-column>
                <el-table-column
                        min-width="23%"
                        align="center"
                        prop="weather"
                        label="天气">
                </el-table-column>
                <el-table-column
                        min-width="23%"
                        align="center"
                        prop="score"
                        label="评分">
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
                            @click="changeStatus(scope.row)"
                    >状态追加</el-button
                    >
                    <el-button
                            type="text"
                            size="small"
                            icon="el-icon-more-outline"
                            @click="showDetail(scope.row.id)"
                    >详情</el-button
                    >
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 分页  -->
        <div class="pagination">
            <el-pagination
                    @background=true
                    @current-change="handleCurrentChange"
                    :current-page.sync="filterForm.pageNum"
                    :page-size="filterForm.pageSize"
                    layout="prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <!-- 新建  -->
        <el-dialog :title="dialogTitle" :before-close="cancel" :visible.sync="showCreateCartoonRecord">
            <el-form :rules="rules" :model="cartoonInfo" ref="cartoonInfo" label-width="120px">
                <el-form-item v-show="!cartoonNameRadio" label="番剧名称" prop="name">
                    <el-radio v-model="cartoonNameRadio" @change="cartoonInfoRadioChange" :disabled="isChangeStatus" :label=false>新增番剧</el-radio>
                    <el-radio v-model="cartoonNameRadio" @change="cartoonInfoRadioChange" :disabled="isChangeStatus" :label=true>选择已有番剧</el-radio>
                    <el-input class="input" v-model="cartoonInfo.name" maxlength="255" placeholder="请输入番剧名称"></el-input>
                </el-form-item>
                <el-form-item v-show="cartoonNameRadio" label="番剧名称" prop="id">
                <el-radio v-model="cartoonNameRadio" @change="cartoonInfoRadioChange" :disabled="isChangeStatus" :label=false>新增番剧</el-radio>
                <el-radio v-model="cartoonNameRadio" @change="cartoonInfoRadioChange" :disabled="isChangeStatus" :label=true>选择已有番剧</el-radio>
                <el-select v-model="cartoonInfo.id" :disabled="isChangeStatus" clearable @clear="clearCartoonInfoSelectList" @change="changeCartoonInfoSelectList" filterable placeholder="请选择番剧">
                    <el-option
                            v-for="item in cartoonInfoList"
                            :key="item.code"
                            :label="item.value"
                            :value="item.code">
                    </el-option>
                </el-select>
                </el-form-item>
                <el-form-item v-show="!cartoonPartNameRadio" label="分季名称" prop="cartoonPart.name">
                    <el-radio v-model="cartoonPartNameRadio" @change="cartoonPartRadioChange" :disabled="isChangeStatus" :label=false>新增分季</el-radio>
                    <el-radio v-model="cartoonPartNameRadio" @change="cartoonPartRadioChange" :disabled="isChangeStatus || cartoonPartDisabledFlag" :label=true>选择已有分季</el-radio>
                    <el-input class="input" v-model="cartoonInfo.cartoonPart.name" maxlength="255" placeholder="请输入分季名称"></el-input>
                </el-form-item>
                <el-form-item v-show="cartoonPartNameRadio" label="分季名称" prop="cartoonPart.id">
                    <el-radio v-model="cartoonPartNameRadio" @change="cartoonPartRadioChange" :disabled="isChangeStatus" :label=false>新增分季</el-radio>
                    <el-radio v-model="cartoonPartNameRadio" @change="cartoonPartRadioChange" :disabled="isChangeStatus || cartoonPartDisabledFlag" :label=true>选择已有分季</el-radio>
                    <el-select v-model="cartoonInfo.cartoonPart.id" :disabled="isChangeStatus" clearable filterable placeholder="请选择分季">
                        <el-option
                                v-for="item in cartoonPartList"
                                :key="item.code"
                                :label="item.value"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="追番状态" prop="cartoonPart.cartoonPartRelation.relationStatus">
                    <el-radio-group size="mini" @change="changeCartoonStatus" v-model="cartoonInfo.cartoonPart.cartoonPartRelation.relationStatus">
                        <el-radio-button label=1>计划追番</el-radio-button>
                        <el-radio-button label=2>开始追番</el-radio-button>
                        <el-radio-button label=3>追番结束</el-radio-button>
                        <el-radio-button label=4>放弃追番</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item v-show="cartoonInfo.cartoonPart.cartoonPartRelation.relationStatus != null" :label="dateLabelName" prop="cartoonPart.cartoonPartRelation.relationDate">
                    <el-date-picker
                            v-model="cartoonInfo.cartoonPart.cartoonPartRelation.relationDate"
                            align="right"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item v-show="cartoonInfo.cartoonPart.cartoonPartRelation.relationStatus == 4" label="放弃原因" prop="cartoonPart.cartoonPartRelation.giveUpReason">
                    <el-input
                            class="text"
                            type="textarea"
                            placeholder="请输入原因"
                            v-model="cartoonInfo.cartoonPart.cartoonPartRelation.comment"
                            maxlength="500"
                            show-word-limit
                    >
                    </el-input>
                </el-form-item>
                <el-form-item v-show="cartoonInfo.cartoonPart.cartoonPartRelation.relationStatus == 3" label="评论" prop="cartoonPart.cartoonPartRelation.comment">
                    <el-input
                            class="text"
                            type="textarea"
                            placeholder="请输入评价"
                            v-model="cartoonInfo.cartoonPart.cartoonPartRelation.comment"
                            maxlength="500"
                            show-word-limit
                    >
                    </el-input>
                </el-form-item>
                <el-form-item v-show="cartoonInfo.cartoonPart.cartoonPartRelation.relationStatus == 3" label="评分" prop="cartoonPart.cartoonPartRelation.rate">
                    <el-rate
                            v-model="cartoonInfo.cartoonPart.cartoonPartRelation.rate"
                            :colors="colors">
                    </el-rate>
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
        <el-dialog title="追番记录详情" :visible.sync="showDetailCartoonRecord">
            <div>
                <h4>番剧名称：{{cartoonRelationDetails.cartoonInfo.name}}</h4>
            </div>
            <div class="timeline-head">
                <h4>分季名称：{{cartoonRelationDetails.name}}</h4>
            </div>
            <el-timeline>
                <el-timeline-item
                        class="timeline"
                        v-for="(cartoonPartRelation, index) in cartoonRelationDetails.cartoonPartRelations"
                        :key="index"
                        color="#409eff"
                        :timestamp="cartoonPartRelation.relationDate"
                        placement="top">
                    <el-card @click.native="editCartoonRelation(cartoonPartRelation)">
                        <p>状态：{{cartoonPartRelation.relationStatusName}}</p>
                        <p v-if="cartoonPartRelation.comment != null">评论：{{cartoonPartRelation.comment}}</p>
                        <el-rate
                                disabled
                                v-if="cartoonPartRelation.rate != null"
                                v-model="cartoonPartRelation.rate"
                                :colors="colors">
                        </el-rate>
                        <p v-if="cartoonPartRelation.updateTime != null">更新时间：{{cartoonPartRelation.updateTime.replace('T',' ')}}</p>
                        <p v-if="cartoonPartRelation.updateTime == null">更新时间：{{cartoonPartRelation.createTime.replace('T',' ')}}</p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>
        </el-dialog>

    </div>
</template>

<script>
    import {
        getCartoonParts,
        getCartoonRelationDetail,
        getAllCartoonInfos,
        getAllCartoonParts,
        addCartoonRecord,
        exportCartoonRecords,
        uploadCartoonRecords,
    } from '@/api/cartoon'

    export default {
        methods: {
            // 行颜色
            tableRowClassName({row}) {
                if (row.cartoonPartRelation.relationStatus == 3) {
                    // 追番完成
                    return 'success-row';
                } else if (row.cartoonPartRelation.relationStatus == 4) {
                    // 看过一部分但 不想再追了
                    return 'warning-row';
                }
                return '';
            },

            // 导出
            async exportCartoonRecord() {
                await exportCartoonRecords(1)
            },

            // 导入
            beforeUpload(file) {
                let extension = file.name.substring(file.name.lastIndexOf('.')+1);
                let size = file.size / 1024 / 1024;
                if(extension !== 'xlsx') {
                    this.$message.warning('只能上传后缀是.xlsx的文件');
                }
                if(size > 10) {
                    this.$message.warning('文件大小不得超过10M');
                }
            },
            uploadSuccess() {
                this.$message.success('文件上传成功');
            },
            uploadError() {
                this.$message.error('文件上传失败');
            },
            exceedFile() {
                this.$message.warning(`只能选择 1 个文件`);
            },
            async uploadHttpRequest(param) {
                let form = new FormData();
                form.append('file', param.file);
                await uploadCartoonRecords(form, 1);
            },
            async uploadFile() {
                let form = new FormData();
                form.append('file', this.fileList);
                await uploadCartoonRecords(form, 1);
            },

            // 详情
            async showDetail(partId) {
                const res = await getCartoonRelationDetail(partId, 1);
                this.cartoonRelationDetails = res.data;

                this.showDetailCartoonRecord = true;
            },
            editCartoonRelation(cartoonPartRelation) {
                this.dialogTitle = "编辑状态"
                this.showCreateCartoonRecord = true
                this.isChangeStatus = true
                this.isEditStatus = true

                this.cartoonNameRadio = true
                this.cartoonPartNameRadio = true
                this.getAllCartoonInfos().then(() => {
                    this.cartoonInfo.id = this.cartoonRelationDetails.cartoonInfo.id.toString()
                })
                this.getAllCartoonParts().then(() => {
                    this.cartoonInfo.cartoonPart.id = this.cartoonRelationDetails.id.toString()
                })
                this.cartoonInfo.cartoonPart.cartoonPartRelation.id = cartoonPartRelation.id;
                this.cartoonInfo.cartoonPart.cartoonPartRelation.comment = cartoonPartRelation.comment;
                this.cartoonInfo.cartoonPart.cartoonPartRelation.rate = cartoonPartRelation.rate;
                this.cartoonInfo.cartoonPart.cartoonPartRelation.relationStatus = cartoonPartRelation.relationStatus;
                this.cartoonInfo.cartoonPart.cartoonPartRelation.relationDate = cartoonPartRelation.relationDate;

                this.rules.name = [];
                this.rules["cartoonPart.name"] = [];
            },

            // 变更状态
            changeStatus(row) {
                this.dialogTitle = "状态追加"
                this.showCreateCartoonRecord = true
                this.isChangeStatus = true

                this.cartoonNameRadio = true
                this.cartoonPartNameRadio = true
                this.getAllCartoonInfos().then(() => {
                    this.cartoonInfo.id = row.cartoonInfo.id.toString()
                })
                this.getAllCartoonParts().then(() => {
                    this.cartoonInfo.cartoonPart.id = row.id.toString()
                })

                this.rules.name = [];
                this.rules["cartoonPart.name"] = [];
            },

            // 新建
            createCartoonRecord() {
                this.showCreateCartoonRecord = true
                this.isChangeStatus = false
                this.dialogTitle = "新增追番记录"
                this.getAllCartoonInfos()
                this.getAllCartoonParts()
            },
            async submitForm() {
                this.$refs['cartoonInfo'].validate( async (valid) => {
                    if (valid) {
                        const res = await addCartoonRecord(this.cartoonInfo, 1);
                        if (res.code == 200) {
                            this.isChangeStatus = false;
                            this.showCreateCartoonRecord = false;
                            this.$refs['cartoonInfo'].resetFields()
                            // 重置按钮
                            this.cartoonNameRadio= false;
                            this.cartoonPartNameRadio= false;
                            // 重置校验规则
                            this.rules.name = [].concat(this.nameRule);
                            this.rules.id = [];
                            this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                            this.rules["cartoonPart.id"] = [];


                            if (this.isEditStatus) {
                                this.showDetail(this.cartoonRelationDetails.id)
                                this.isEditStatus = false;
                            }
                            this.getCartoonByName();
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
                this.showCreateCartoonRecord = false;
                this.$refs['cartoonInfo'].resetFields();
                this.cartoonInfo.cartoonPart.cartoonPartRelation = {}
                // 重置按钮
                this.cartoonNameRadio= false;
                this.cartoonPartNameRadio= false;
                // 重置校验规则
                this.rules.name = [].concat(this.nameRule);
                this.rules.id = [];
                this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                this.rules["cartoonPart.id"] = [];

                this.getCartoonByName();
                // done();
            },
            cartoonInfoRadioChange() {
                if (this.cartoonNameRadio == false) {
                    //新增
                    this.cartoonPartNameRadio = false;
                    this.cartoonPartDisabledFlag = true;
                    if (this.cartoonInfo.id != null) {
                        this.cartoonInfo.id = null;
                    }
                    if (this.cartoonInfo.cartoonPart.id != null) {
                        this.cartoonInfo.cartoonPart.id = null;
                    }
                    this.rules.name = [].concat(this.nameRule);
                    this.rules.id = [];
                    this.$refs['cartoonInfo'].clearValidate(['id']);
                    this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                    this.rules["cartoonPart.id"] = [];
                    this.$refs['cartoonInfo'].clearValidate(['cartoonPart.id']);
                } else {
                    //选择
                    this.rules.id = [].concat(this.idRule);
                    this.rules.name = [];
                    this.$refs['cartoonInfo'].clearValidate(['name']);
                }
            },
            cartoonPartRadioChange() {
                if (this.cartoonPartNameRadio == false) {
                    if (this.cartoonInfo.cartoonPart.id != null) {
                        this.cartoonInfo.cartoonPart.id = null;
                    }
                    this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                    this.rules["cartoonPart.id"] = [];
                    this.$refs['cartoonInfo'].clearValidate(['cartoonPart.id']);
                } else {
                    this.rules["cartoonPart.id"] = [].concat(this.cartoonPartIdRule);
                    this.rules["cartoonPart.name"] = [];
                    this.$refs['cartoonInfo'].clearValidate(['cartoonPart.name']);
                }
            },
            changeCartoonInfoSelectList(val) {
                if (val == null) {
                    this.cartoonPartDisabledFlag = true;
                } else {
                    this.cartoonPartDisabledFlag = false;
                }
                if (this.cartoonInfo.cartoonPart.id != null) {
                    this.cartoonInfo.cartoonPart.id = null;
                }
                this.getAllCartoonParts(val)
            },
            clearCartoonInfoSelectList() {
                this.cartoonPartDisabledFlag = true;
                this.cartoonPartNameRadio = false;
                this.rules["cartoonPart.name"] = [].concat(this.cartoonPartNameRule);
                this.rules["cartoonPart.id"] = [];
                this.$refs['cartoonInfo'].clearValidate(['cartoonPart.id']);
                this.cartoonInfo.cartoonPart.id = null;
                this.getAllCartoonParts(null)
            },
            changeCartoonStatus(val) {
                switch (val)
                {
                    case "1":this.dateLabelName = "计划追番日期";
                        break;
                    case "2":this.dateLabelName = "开始追番日期";
                        break;
                    case "3":this.dateLabelName = "追番结束日期";
                        break;
                    case "4":this.dateLabelName = "放弃追番日期";
                        break;
                    default:
                        this.dateLabelName = "选择日期";
                        break;
                }
                if (this.cartoonInfo.cartoonPart.cartoonPartRelation.comment != null) {
                    this.cartoonInfo.cartoonPart.cartoonPartRelation.comment = null
                }
            },

            // 分页
            handleCurrentChange(val) {
                this.filterForm.pageNum = val
                this.getCampingList()
            },

            // 查询番名
            async getCartoonByName() {
                this.filterForm.pageNum = 1
                this.getCampingList()
            },

            async getCampingList() {
                const res = await getCartoonParts(this.filterForm, 1)
                this.tableData = res.rows;
                this.total = res.total;
            },
            // 获取番剧名称select列表
            async getAllCartoonInfos() {
                const res = await getAllCartoonInfos()
                this.cartoonInfoList = res.data;
            },
            // 获取分季名称select列表
            async getAllCartoonParts(cartoonId) {
                const res = await getAllCartoonParts(cartoonId);
                this.cartoonPartList = res.data;
            },
        },
        created() {
            this.getCartoonByName();
        },
        data() {
            return {
                cartoonRelationDetails: {
                    cartoonInfo:{}
                },
                dialogTitle:"新增露营记录",
                isEditStatus:false,
                isChangeStatus:false,
                showCreateCartoonRecord:false,
                showDetailCartoonRecord:false,
                cartoonNameRadio: false,
                cartoonPartNameRadio: false,
                cartoonPartDisabledFlag: true,

                fileList:[],

                // 新建页面下拉菜单
                cartoonInfoList:[],
                cartoonPartList:[],

                cartoonNames: [],
                cartoonInfo:{
                    id:undefined,
                    name:undefined,
                    cartoonPart:{
                        id:undefined,
                        name:undefined,
                        cartoonPartRelation:{
                            id:undefined,
                            relationStatus: undefined,
                            relationDate: undefined,
                            comment:undefined,
                            rate:undefined
                        },
                    },
                },
                dateLabelName:"选择日期",
                filterForm: {
                    cartoonName: "",
                    pageSize: 10,
                    pageNum: 1
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
    .timeline-head {
        border-bottom: 1px solid #dcdfe6;
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