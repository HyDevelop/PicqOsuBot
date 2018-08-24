// 要移除的类
var classToRemove =
[
    "profile-badges",
    "profile-previous-usernames",
    "supporter-icon",
    "profile-info__title"
];

// 删除回放观看次数
$(".profile-stats__entry").last().remove();

// 获取PP图表, 校正图表位置, 替换图表
var chartHtmlString = $(".profile-header-extra__column--chart").html();
chartHtmlString = chartHtmlString.replace("translate(5, 5)", "translate(0, 8) scale(0.9, 0.9)");
$(".profile-stats__value--score-ranks").html(chartHtmlString);

// 获取HTML Body
var jQueryBody = $("body");
var bodyHtml = jQueryBody.html();

// 添加Padding
bodyHtml = bodyHtml.replace("<div class=\"profile-info\">", "<div class=\"profile-info\" style=\"padding-right: 40px;\">");

// 替换Body HTML
jQueryBody.html(bodyHtml);

// 改为移动视图
// $("meta[name='viewport']").attr("content", "width=400, initial-scale=2");

// 移除要移除的类
classToRemove.forEach(function (className)
{
    $("." + className).remove();
});

// 获取是否Supporter, 如果是, 添加supporter标注
var isSupporter = /"is_supporter":true/g.test(bodyHtml);
if (isSupporter)
{
    $("<span class=\"profile-info__title\">osu! Supporter</span>").insertAfter(".profile-info__name")
}