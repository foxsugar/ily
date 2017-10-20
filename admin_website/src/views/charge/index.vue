<template>

  <div class="app-container calendar-list-container">


    <el-form :inline="true" :model="chargeForm" class="demo-form-inline">
      <el-form-item label="玩家id">
        <el-input v-model="chargeForm.userId" placeholder="玩家id"></el-input>
      </el-form-item>

      <el-form-item label="充值金额">
        <el-input v-model="chargeForm.num" placeholder=""></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">充值</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
  import {charge} from '@/api/player'
  export default {
    data() {
      return {
        chargeForm: {
          userId: '',
          num: ''
        }
      }
    },
    methods: {
      onSubmit() {

//        alert(JSON.stringify(this.chargeForm))
        charge(this.chargeForm).then(response => {

          console.log(response)
          this.$message({
            message: '充值成功',
            type: 'success'
          });
        });
        console.log('submit!');
      }
    }
  }
</script>
