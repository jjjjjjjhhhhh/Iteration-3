<template>
	<div class="edit_view" :style='{}'>
        <div class="breadcrumb-wrapper" style="width: 100%;">
            <div class="bread_view">
                <el-breadcrumb separator="/" class="breadcrumb">
                    <el-breadcrumb-item class="first_breadcrumb" :to="{ path: '/' }">Home</el-breadcrumb-item>
                    <el-breadcrumb-item class="second_breadcrumb" v-for="(item,index) in breadList" :key="index">{{item.name}}</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </div>
		<el-form ref="formRef" :model="form" class="add_form" label-width="120px" :rules="rules">
			<el-row>
				<el-col :span="12">
					<el-form-item label="User ID" prop="uid">
						<el-input class="list_inp"
                                  v-model="form.uid"
                                  placeholder="User ID"
                                  type="text"
							      :readonly="!isAdd||disabledForm.uid?true:false" />
					</el-form-item>
				</el-col>

				<el-col :span="12">
					<el-form-item label="Friend ID" prop="fid">
						<el-input class="list_inp"
                                  v-model="form.fid"
                                  placeholder="Friend ID"
                                  type="text"
							      :readonly="!isAdd||disabledForm.fid?true:false" />
					</el-form-item>
				</el-col>

				<el-col :span="12">
					<el-form-item label="Content" prop="content">
						<el-input class="list_inp"
                                  v-model="form.content"
                                  placeholder="Content"
                                  type="text"
							      :readonly="!isAdd||disabledForm.content?true:false" />
					</el-form-item>
				</el-col>

				<el-col :span="12">
					<el-form-item label="Format (1:Text, 2:Image)" prop="format">
						<el-input class="list_inp"
                                  v-model.number="form.format"
                                  placeholder="Format (1:Text, 2:Image)"
                                  type="text"
							      :readonly="!isAdd||disabledForm.format?true:false" />
					</el-form-item>
				</el-col>

				<el-col :span="12">
					<el-form-item label="Message Read (0:Unread, 1:Read)" prop="isRead">
						<el-input class="list_inp"
                                  v-model.number="form.isRead"
                                  placeholder="Message Read (0:Unread, 1:Read)"
                                  type="text"
							      :readonly="!isAdd||disabledForm.isRead?true:false" />
					</el-form-item>
				</el-col>

			</el-row>
			<div class="formModel_btn_box">
				<el-button class="formModel_cancel" @click="backClick">Cancel</el-button>
				<el-button class="formModel_confirm"
                           @click="save"
                           type="success"
				>
					Save
				</el-button>
			</div>
		</el-form>
	</div>
</template>
<script setup>
	import {
		ref,
		getCurrentInstance,
		watch,
		onUnmounted,
		onMounted,
		nextTick,
		computed
	} from 'vue';
	import {
		useRoute,
		useRouter
	} from 'vue-router';
    import moment from "moment";
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const route = useRoute()
	const router = useRouter()
	// Basic Information
	const tableName = 'chatmessage'
	const formName = 'Message List'
	// Basic Information
	const breadList = ref([{
		name: formName
	}])
	// Get Unique Identifier
	const getUUID =()=> {
      return new Date().getTime();
    }
	// Form
	const form = ref({
		uid: '',
		fid: '',
		content: '',
		format: 0,
	})
	const formRef = ref(null)
	const id = ref(0)
	const type = ref('')
	const disabledForm = ref({
		uid : false,
		fid : false,
		content : false,
		format : false,
		isRead : false,
	})
	const isAdd = ref(false)
	// Form Validation
	const rules = ref({
		uid: [
			{required: true,message: 'Please enter',trigger: 'blur'}, 
		],
		fid: [
			{required: true,message: 'Please enter',trigger: 'blur'}, 
		],
		content: [
		],
		format: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		isRead: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
	})
	// Methods

	// Methods
	// Get Info
	const getInfo = ()=>{
		context?.$http({
			url: `${tableName}/info/${id.value}`,
			method: 'get'
		}).then(res => {
			let reg=new RegExp('../../../file','g')
			form.value = res.data.data
		})
	}
	const crossRow = ref('')
	const crossTable = ref('')
	const crossTips = ref('')
	const crossColumnName = ref('')
	const crossColumnValue = ref('')
	// Initialize
	const init = (formId=null,formType='add',formNames='',row=null,table=null,statusColumnName=null,tips=null,statusColumnValue=null) => {
		if(formId){
			id.value = formId
			type.value = formType
		}
		if(formType == 'add'){
			isAdd.value = true
		}else if(formType == 'info'){
			isAdd.value = false
			getInfo()
		}else if(formType == 'edit'){
			isAdd.value = true
			getInfo()
		}
		else if(formType == 'cross'){
			isAdd.value = true
			// getInfo()
			for(let x in row){
				if(x=='uid'){
					form.value.uid = row[x];
					disabledForm.value.uid = true;
					continue;
				}
				if(x=='fid'){
					form.value.fid = row[x];
					disabledForm.value.fid = true;
					continue;
				}
				if(x=='content'){
					form.value.content = row[x];
					disabledForm.value.content = true;
					continue;
				}
				if(x=='format'){
					form.value.format = row[x];
					disabledForm.value.format = true;
					continue;
				}
				if(x=='isRead'){
					form.value.isRead = row[x];
					disabledForm.value.isRead = true;
					continue;
				}
			}
		}
	}
	// Initialize
	// Cancel
	const backClick = () => {
		history.back()
	}
	// Submit
	const save=()=>{
		var table = crossTable.value
		var objcross = JSON.parse(JSON.stringify(crossRow.value))
		let crossUserId = ''
		let crossRefId = ''
		let crossOptNum = ''
		if(type.value == 'cross'){
			if(crossColumnName.value!=''){
				if(!crossColumnName.value.startsWith('[')){
					for(let o in objcross){
						if(o == crossColumnName.value){
							objcross[o] = crossColumnValue.value
						}
					}
					// Modify cross table data
					changeCrossData(objcross)
				}else{
					crossUserId = context?.$toolUtil.storageGet('userid')
					crossRefId = objcross['id']
					crossOptNum = crossColumnName.value.replace(/\[/,"").replace(/\]/,"")
				}
			}
		}
		formRef.value.validate((valid)=>{
			if(valid){
				if(crossUserId&&crossRefId){
					form.value.crossuserid = crossUserId
					form.value.crossrefid = crossRefId
					let params = {
						page: 1,
						limit: 1000, 
						crossuserid:form.value.crossuserid,
						crossrefid:form.value.crossrefid,
					}
					context?.$http({
						url: `${tableName}/page`,
						method: 'get', 
						params: params 
					}).then(res=>{
						if(res.data.data.total>=crossOptNum){
							context?.$toolUtil.message(`${crossTips.value}`,'error')
							return false
						}else{
							context?.$http({
								url: `${tableName}/${!form.value.id ? "save" : "update"}`,
								method: 'post', 
								data: form.value 
							}).then(res=>{
                                context?.$toolUtil.message(`Operation successful`,'success')
                                history.back()
							})
						}
					})
				}else{
					context?.$http({
						url: `${tableName}/${!form.value.id ? "save" : "update"}`,
						method: 'post', 
						data: form.value 
					}).then(res=>{
                        context?.$toolUtil.message(`Operation successful`,'success')
                        history.back()
					})
				}
			}
		})
	}
	// Modify cross table data
	const changeCrossData=(row)=>{
        if(type.value == 'cross'){
            context?.$http({
                url: `${crossTable.value}/update`,
                method: 'post',
                data: row
            }).then(res=>{})
        }
	}
	onMounted(()=>{
		type.value = route.query.type?route.query.type:'add'
		let row = null
		let table = null
		let statusColumnName = null
		let tips = null
		let statusColumnValue = null
		if(type.value == 'cross'){
			row = context?.$toolUtil.storageGet('crossObj')?JSON.parse(context?.$toolUtil.storageGet('crossObj')):{}
			table = context?.$toolUtil.storageGet('crossTable')
			statusColumnName = context?.$toolUtil.storageGet('crossStatusColumnName')
			tips = context?.$toolUtil.storageGet('crossTips')
			statusColumnValue = context?.$toolUtil.storageGet('crossStatusColumnValue')
		}
		init(route.query.id?route.query.id:null, type.value,'', row, table, statusColumnName, tips, statusColumnValue)
	})
    onUnmounted(()=>{
        Object.keys(localStorage).map(item=>{
            if(item.startsWith('cross')){
                localStorage.removeItem(item)
            }
        })
    })
</script>
<style lang="scss" scoped>
	// 面包屑盒子
	.bread_view {
		:deep(.breadcrumb) {
			.el-breadcrumb__separator {
			}
			.first_breadcrumb {
				.el-breadcrumb__inner {
				}
			}
			.second_breadcrumb {
				.el-breadcrumb__inner {
				}
			}
		}
	}
	// 表单
	.add_form{
		// form item
		:deep(.el-form-item) {
			//label
			.el-form-item__label {
			}
			// 内容盒子
			.el-form-item__content {
				// 输入框
				.list_inp {

				}
			}
		}
	}
	// 按钮盒子
	.formModel_btn_box {
		.formModel_cancel {
		}
		.formModel_cancel:hover {
		}
		
		.formModel_confirm {
		}
		.formModel_confirm:hover {
		}
	}
</style>
<style lang="scss">
.edit_view {
    width: 1200px;
    margin: 20px auto;
    padding: 0px ;
    background: #fff;
    overflow: hidden;
    font-size: 16px;
    color:#666;
}
.edit_view .add_form{
    width: 100%;
    padding: 30px;
    border:0px solid #eee
}
.edit_view .add_form .el-form-item{
    margin: 0px 0px 20px;
    display: flex;
}
.edit_view .add_form .el-form-item .el-form-item__label{
    width: 150px;
    background: none;
    text-align: right;
    display: block;
    font-size: 16px;
    color: rgb(51, 51, 51);
    font-weight: 500;
}
.edit_view .add_form .el-form-item .el-form-item__content{
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-wrap: wrap;
    width: calc(100% - 120px);
}
.edit_view .add_form .el-form-item .el-form-item__content .list_inp{
    height: 36px;
    line-height: 36px;
    border: 1px solid rgb(221, 221, 221);
    padding: 0px 10px;
    width: 100%;
    box-sizing: border-box;
    background: rgb(255, 255, 255);
    font-size: 16px;
}










</style>