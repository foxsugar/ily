<template>

  <div class="app-container calendar-list-container">


    <el-form  :model="constantForm" label-width="100px">

      <el-form-item label="初始金额" :width="100">
        <el-input v-model="constantForm.init_money" :max="100" :width="100"></el-input>
      </el-form-item>

      <el-form-item label="安卓版本">
        <el-input v-model="constantForm.version_of_android" placeholder=""></el-input>
      </el-form-item>

      <el-form-item label="苹果版本">
        <el-input v-model="constantForm.version_of_ios" placeholder=""></el-input>
      </el-form-item>

      <el-form-item label="appleCheck">
        <el-input v-model="constantForm.apple_check" placeholder=""></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">充值</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
  import {constant} from '@/api/constant'
  export default {
    data() {
      return {
        constantForm: {
          id: '',
          init_money: 0,
          apple_check: '',
          version_of_android: '',
          version_of_ios: '',
          marquee: '',
          marquee1: '',
          marquee2: '',
          download: '',
          download2: ''

        }
      }
    },
    created(){
      this.get()
    },
    methods: {

      get(){
        constant('GET',this.constantForm).then(response => {
          this.constantForm = response.data;
        });
      },
      onSubmit() {
        charge(this.constantForm).then(response => {
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
