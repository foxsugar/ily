<template>
  <div>



    <!--搜索框 + 表格-->
    <div class="app-container calendar-list-container">

      <div class="filter-container">
        <el-input @keyup.enter.native="handleClick" style="width: 150px;" class="filter-item"
                  placeholder="名称"></el-input>

        <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleClick">搜索</el-button>
        <el-button class="filter-item" style="margin-left: 10px;" @click="addProduct" type="primary" icon="plus">
          添加应用
        </el-button>


      </div>
      <br/>

      <!--表格-->
      <el-table :data="tableData" v-loading.body="listLoading" element-loading-text="给我一点时间" stripe border fit
                highlight-current-row style="width: 100%">

        <el-table-column align="center" prop="trade_id" label="流水号" width="200"></el-table-column>
        <el-table-column align="center" prop="order_id" label="订单号" width="200"></el-table-column>
        <el-table-column align="center" prop="sub_order_id" label="子订单" width="200"></el-table-column>
        <el-table-column align="center" prop="order_type" label="代码方" width="200">
          <template scope="scope">
            <span>{{getOptionKey(scope.row.order_type)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="app_id" label="app_id" width="200"></el-table-column>
        <el-table-column align="center" prop="channel_id" label="channel_id" width="200"></el-table-column>

        <el-table-column align="center" prop="price" label="金额" width="200"></el-table-column>
        <el-table-column align="center" prop="order_time" label="订单时间" width="200"></el-table-column>
        <el-table-column align="center" prop="province" label="地区" width="200"></el-table-column>


        <!--<el-table-column align="center" fixed="right" label="操作" min-width="150">-->
          <!--<template scope="scope">-->
            <!--<el-button @click="editTable(scope)" type="primary" size="small">编辑</el-button>-->
            <!--<el-button @click="doDeleteAgent" type="danger" size="small">删除</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
    </div>

    <div class="block">
      <span class="demonstration"></span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="page_sizes"
        :page-size="page_size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalPage">
      </el-pagination>
    </div>

    <br>

  </div>

</template>

<script>
  import {getList} from '@/api/order'
  import waves from '@/directive/waves.js'
  import ElForm from "../../../node_modules/element-ui/packages/form/src/form";
  // 水波纹指令

  export default {
    components: {ElForm},
    directives: {
      waves
    },

    methods: {

      addProduct(){
        this.dialogFormVisible = true
      },
      editTable(scope){
        this.editFormVisible = true;
        this.productForm.id = scope.row.id
        this.productForm.name = scope.row.name
        console.log(scope.row.code_id)
//        this.productForm.code_id = this.getOptionKey(scope.row.code_id)
        this.productForm.code_id = scope.row.code_id
        console.log(  this.productForm.code_id)
        this.productForm.app_id = scope.row.app_id
        this.productForm.channel_id = scope.row.channel_id
        this.productForm.description = scope.row.description

        this.doUpdateAgent("productForm")


      },
      doAddProduct(formName){
        this.$refs[formName].validate((valid) => {
          //本地验证
          if (valid) {
            product(this.productForm, 'POST').then(response => {
              this.$message({
                message: '添加成功',
                type: 'success'
              });
              this.dialogFormVisible = false;
              //重新获取数据
              this.fetchData()

            })
          } else {
            console.log('提交失败');
            return false;
          }
        });

      },

      handleClick(){
      },
      doDeleteAgent(){
        product(this.productForm, 'DELETE').then(response => {

        })
      },
      doUpdateAgent(formName){
        this.$refs[formName].validate((valid) => {
          //本地验证
          if (valid) {
            product(this.productForm, 'PUT').then(response => {
              this.$message({
                message: '编辑成功',
                type: 'success'
              });
              this.editFormVisible = false;
              //重新获取数据
              this.fetchData()

            })
          } else {
            console.log('提交失败');
            return false;
          }
        });

      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)
        this.page_size = val;
        this.fetchData()
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.currentPage = val
        this.fetchData()
      },
      fetchData() {
        this.listLoading = true;
        getList(this.currentPage, this.page_size).then(response => {
          this.tableData = response.data.tableData;
          this.totalPage = response.data.totalPage;
          this.listLoading = false;
        });

      },
      handleClose(done){
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },

      getOptionKey(value){
        for (var i = 0; i < this.options.length; i++) {
          var opt = this.options[i];
          if (value == opt.value) {
            return opt.label
          }

        }

      }

    },

    created() {
      this.fetchData()
    },
    data() {
      return {
        listLoading: true,
        tableData: [],
        totalPage: 0,
        currentPage: 1,
        page_size: 20,
        page_sizes: [20, 50, 100, 200],

        dialogTableVisible: false,
        dialogFormVisible: false,
        editFormVisible: false,
        chargeDialogFormVisible: false,
        productForm: {
          id: 0,
          name: '',
          description: '',
          create_time: '',
          code_id: '',
          app_id: '',
          channel_id: ''
        },

        formLabelWidth: '120px',


        options: [{
          value: '1',
          label: '微云'
        }, {
          value: '2',
          label: '平治'
        }, {
          value: '3',
          label: '麦广'
        }, {
          value: '4',
          label: '中至'
        }],
        value: ''

      }
    }
  }
</script>
