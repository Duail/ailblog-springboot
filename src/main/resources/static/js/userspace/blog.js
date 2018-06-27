"use strict";
//# sourceURL=blog.js

// DOM 加载完再执行
$(function() {
    $.catalog("#catalog", ".post-content");

    // 处理删除博客事件
    $(".deleteBlog").click( function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");


        $.ajax({
            url: blogUrl,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    // 成功后，重定向
                    // window.location = data.body;
                    location.assign(data.body);
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    //getComments
    function getComment(blogId) {
        $.ajax({
            url: '/comments',
            type: 'GET',
            data: {"blogId": blogId},
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    //提交评论
    $("#submitComment").click( function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/comments',
            type: 'POST',
            data: {"blogId": blogId, "commentContent": $(".blog-textarea").val()},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                if (data.success) {
                    //清空评论框
                    $(".blog-textarea").val("");
                    getComment(blogId);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    //删除评论
    $(".blog-content-container").on("click",".delete-comment", function () {
        debugger;
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/comments/'+$(this).attr("commentId")+'?blogId='+blogId,
            type: 'DELETE',
            // data: {"blogId":blogId},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                if (data.success) {
                    getComment(blogId);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");

            }
        });
    });

    // 点赞
    $("#create-vote").click( function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");


        $.ajax({
            url: '/votes',
            type: 'POST',
            data: {"blogId":blogId},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    // 成功后，重定向
                    // window.location = data.body;
                    location.assign(blogUrl);
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // 取消点赞
    $("#remove-vote").click( function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");


        $.ajax({
            url: '/votes/'+$(this).attr("voteId")+'?blogId='+blogId,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    // 成功后，重定向
                    // window.location = data.body;
                    location.assign(blogUrl);
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // 初始化 博客评论
    getComment(blogId);
});