// 重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

// 重写confirm式样框
window.confirm = function(msg, callback, callback2){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(index) { // 确定事件
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok")
		}
	},function (index2) {
      parent.layer.close(index2);
      if(typeof(callback2) === "function"){
        callback2("ok")
      }
    });
}

//重写open式样框
window.open = function(callback){
  parent.layer.open({type: 1,
      offset: '50px',
      skin: 'layui-layer-molv',
      title: "选择菜单",
      area: ['300px', '450px'],
      shade: 0,
      shadeClose: false,
      content: jQuery("#menuLayer"),
      btn: ['确定','取消'],
      btn1: function(index) {//确定事件
        if (typeof(callback) === "function") {
          callback();
        }
        parent.layer.close(index);
      }
    })
}

window.ztree = $.fn.zTree

