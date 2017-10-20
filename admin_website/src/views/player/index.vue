<template>
  <div>


    <!--搜索框 + 表格-->
    <div class="app-container calendar-list-container">

      <div class="filter-container">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="用户名"
                  v-model="listQuery.title">
        </el-input>
        <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      </div>
      <br/>

      <!--表格-->
      <el-table :data="tableData" v-loading.body="listLoading" element-loading-text="给我一点时间" stripe border fit
                highlight-current-row style="width: 100%">

        <el-table-column align="center" prop="id" label="id" width="120"></el-table-column>

        <!--<el-table-column align="center" fixed prop="image" label="image" width="500">-->
        <!--<template scope="scope">-->
        <!--<img  :src="scope.row.image+'/96?'">-->
        <!--</template>-->
        <!--</el-table-column>-->

        <el-table-column align="center" prop="username" label="用户名" width="200"></el-table-column>

        <el-table-column align="center" prop="account" label="账号" width="350"></el-table-column>

        <el-table-column align="center" prop="password" label="密码" width="100"></el-table-column>

        <el-table-column align="center" prop="sex" label="性别" width="100">
          <template scope="scope">
            <span>{{scope.row.sex == 1 ? '男' : '女'}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" prop="money" label="房卡" width="150"></el-table-column>

        <el-table-column align="center" prop="gold" label="积分" width="150"></el-table-column>

        <el-table-column align="center" prop="referee" label="代理" width="150"></el-table-column>

        <el-table-column align="center" prop="email" label="邮箱" width="150"></el-table-column>

        <el-table-column align="center" label="操作" width="200">
          <template scope="scope">
            <el-button @click="handleChargeClick(scope)" type="primary" size="small">充值</el-button>
          </template>
        </el-table-column>

        <el-table-column align="center" fixed="right" label="操作" min-width="220">
          <template scope="scope">
            <el-button :type="scope.row.edit?'success':'primary'" @click='handleEditClick(scope)' size="small"
                       icon="edit">{{scope.row.edit ? '完成' : '编辑'}}
            </el-button>
            <!--<el-button @click="handleClick" type="primary" size="small">编辑</el-button>-->
            <el-button @click="handleClick" type="danger" size="small">删除</el-button>
          </template>
        </el-table-column>

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
  import {getList, fetchList} from '@/api/player'
  import {charge} from '@/api/player'
  import waves from '@/directive/waves.js'// 水波纹指令

  export default {
    directives: {
      waves
    },
    methods: {

      handleFilter(){
        this.listQuery.page = 1
        console.log(this.listQuery)
        this.getFilterList()
      },

      getFilterList() {
        this.listLoading = true
        fetchList(this.listQuery).then(response => {
          this.list = response.data.items
          this.total = response.data.total
          this.listLoading = false
          this.tableData = response.data.tableData
          this.totalPage = response.data.totalPage
          this.listLoading = false
        })
      },
      handleClick() {
        this.dialogFormVisible = true;
      },
      handleChargeClick(scope) {
        this.chargeDialogFormVisible = true;
        this.chargeForm.id = scope.row.id;
        this.chargeForm.username = scope.row.id;
        this.chargeForm.num = 0;
      },
      doCharge() {
        charge(this.chargeForm).then(response => {
          this.tableData.forEach(td => {
            if (td.id == this.chargeForm.id) {
              td.money = response.data;
            }
            this.$message({
              message: '充值成功',
              type: 'success'
            });
          })
        })
        this.chargeDialogFormVisible = false
      },


      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)
        this.page_size = val;
        this.fetchData()
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.currentPage = val;
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
        chargeDialogFormVisible: false,
        chargeForm: {
          id: '',
          username: '',
          num: 0
        },
        listQuery: {
          page: 1,
          limit: 20,
          importance: undefined,
          title: undefined,
          type: undefined,
          sort: '+id'
        },
        formLabelWidth: '120px',


      }
    }
  }
</script>
