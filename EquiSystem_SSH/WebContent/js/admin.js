/*
	主页加载方法
	@eric
*/
//系统时间显示
setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	view: {
		selectedMulti: false
	},
	callback: {
		onClick:function(e, id, node){
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			if(node.isParent) {
				zTree.expandNode();
			} else {
				addTabs(node.name, node.file);
			}
		}
	}
};

var zNodes =[
	{ id:1, pId:0, name:"实验教学与开放", open:true},
	{ id:11, pId:1, name:"实验室教学管理", open:true},
	{ id:111, pId:11, name:"实验课程预约管理", file:"teachAndOpen/labTeach/course_table.jsp"},
	{ id:12, pId:1, name:"实验室开放管理", open:true},
	{ id:121, pId:12, name:"实验室信息", file:"teachAndOpen/labBook/lab_equi.jsp"},
	{ id:121, pId:12, name:"实验室预约管理", file:"teachAndOpen/labBook/lab_bespeak.jsp"},
	{ id:2, pId:0, name:"实践创新管理", open:true},
	{ id:21, pId:2, name:"大学生创新竞赛管理", open:true},	
	{ id:211, pId:21, name:"竞赛项目", file:"practiceAndInnovation/innoContest/comp_project.jsp"},
	{ id:212, pId:21, name:"大创项目", file:"practiceAndInnovation/innoContest/inno_project.jsp"},
	{ id:3, pId:0, name:"设备物资管理", open:true},
	{ id:31, pId:3, name:"设备、物资管理", open:true},
	{ id:311, pId:31, name:"书架管理", file:"equiAndGoods/equiManager/book.jsp"},
	{ id:312, pId:31, name:"图书借阅管理", file:"equiAndGoods/equiManager/book_borrow.jsp"},
	{ id:313, pId:31, name:"教室设备管理", file:"equiAndGoods/equiManager/room_equi.jsp"},
	{ id:314, pId:31, name:"设备借用管理", file:"equiAndGoods/equiManager/equi_borrow.jsp"},
	/*{ id:315, pId:31, name:"期末统计", file:""},*/
	{ id:32, pId:3, name:"设备、物资采购管理", open:true},
	{ id:321, pId:32, name:"预采购管理", file:"equiAndGoods/equiBuy/equi_paylist.jsp"},
	{ id:322, pId:32, name:"设备领取管理", file:"equiAndGoods/equiBuy/equi_get.jsp"},
	{ id:33, pId:3, name:"设备报修管理", open:true},
	{ id:331, pId:33, name:"报修处理", file:"equiAndGoods/equiRepair/equi_repair.jsp"},
	{ id:4, pId:0, name:"信息门户", open:true},
	{ id:41, pId:4, name:"实验楼资料", file:"informationPortal/upload_download.jsp"},
	{ id:42, pId:4, name:"学生业务", open:true},
	{ id:421, pId:42, name:"实验报告", file:"informationPortal/studentService/experiment_report.jsp"},
	{ id:43, pId:4, name:"账号管理", file:"informationPortal/manage.jsp"}
	
	
];

$(function() {
	$.fn.zTree.init($("#menuTree"), setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("menuTree");
	
	//中间部分tab
	$('#tabs').tabs({  
		border:false,
		fit:true,
		onSelect: function(title, index){
			var treeNode = zTree.getNodeByParam("name", title, null);
			zTree.selectNode(treeNode);
		}
	}); 
	
	$('.index_panel').panel({  
	  width:300,  
	  height:200,  
	  closable:true,
	  minimizable:true,
	  title: 'My Panel'
	});
	
});

//添加一个选项卡面板 
function addTabs(title, url, icon){
	if(!$('#tabs').tabs('exists', title)){
		$('#tabs').tabs('add',{  
			title:title,  
			content:'<iframe src="'+url+'" frameBorder="0" border="0" scrolling="no" style="width: 100%; height: 100%;"/>',  
			closable:true
		});
	} else {
		$('#tabs').tabs('select', title);
	}
}