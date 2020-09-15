layui.use('form', function () {
    var form = layui.form
        ,layer = layui.layer;
});
function submitsignup() {
    var username=$("#username").val();
    var password=$("#password").val();
    var password2=$("#password2").val();
    var nickname = $("#nickname").val();
    if (username.length===0) {
        layer.tips("Please enter your username", '#username', {
            tips: [1, "#0FA6D8"],
            tipsMore: !1,
            time: 2000
        });
        $("#username").focus();
        return false;
    }
    if (password.length===0) {
        layer.tips("Please enter your password",'#password',{
            tips: [1, "#0FA6D8"],
            tipsMore: !1,
            time: 2000
        });
        $("#password").focus();
        return false;
    }
    if (password2.length===0) {
        layer.tips("Please enter your password",'#password2',{
            tips: [1, "#0FA6D8"],
            tipsMore: !1,
            time: 2000
        });
        $("#password2").focus();
        return false;
    }
    if (nickname.length===0) {
        layer.tips("Please enter your nickname", '#nickname', {
            tips: [1, "#0FA6D8"],
            tipsMore: !1,
            time: 2000
        });
        $("#nickname").focus();
        return false;
    }
    var object = new Object(); //创建一个存放数据的对象
    object["username"] = username;
    object["password"] = password;
    object["password2"] = password2;
    object["nickname"] = nickname;
    object["uimg"] = uimg;
    var jsonData = JSON.stringify(object); //根据数据生成json数据
    $.ajax({
        url: basePath+"/dosignup",
        data: jsonData,
        contentType: "application/json;charset=UTF-8", //发送数据的格式
        type: "post",
        dataType: "json", //回调
        beforeSend: function () {
            layer.msg('loading', {
                icon: 16
                ,shade: 0.01
            });
        },
        complete: function () {
            layer.closeAll('loading');
        },
        success: function (data) {
            if(data.status!=200){
                layer.msg(data.message, {
                    time: 1500,
                    icon: 2,
                    offset: '350px'
                });
            }else{
                layer.msg(data.message, {
                    time: 1000,
                    icon: 1,
                    offset: '350px'
                }, function () {
                    location.href=basePath+"/login";
                });
            }
        }
    });
}
var uimg;
layui.use('upload', function(){
    var upload = layui.upload;
     
    //执行实例
    var uploadInst = upload.render({
      elem: '#img-upload' //绑定元素
      ,url: basePath + '/chat/setimg', 
      type: 'POST', 
      size: 1024 * 5
      ,done: function(res){
        uimg=res.uimg;
        console.log(res.uimg);
      }
      ,error: function(){
        console.log("upload pics error!");
      }
    });
});
