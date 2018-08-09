// 用来检测的正则
var regexChart = /<div class="profile-header-extra__rank-box">.*?pp<\/div>/g;
var regexReplay = /<dl class="profile-stats__entry"><dt class="profile-stats__key">回放被观看次数<\/dt><dd class="profile-stats__value">.*?<\/dd><\/dl><div class="profile-stats__value profile-stats__value--score-ranks">.*?[0-9]<\/div><\/div><\/div>/g;

// 要移除的类
var classToRemove =
[
    "profile-badges",
    "profile-previous-usernames",
    "supporter-icon",
    "profile-info__title"
];

// 获取HTML Body
var jQueryBody = $("body");
var bodyHtml = jQueryBody.html();

// 获取PP图表, 校正图表位置, 替换图表
var chartHtmlString;
var chartMatcher = regexChart.exec(bodyHtml);
if (chartMatcher) chartHtmlString = chartMatcher[0].toString();
chartHtmlString = chartHtmlString.replace("translate(5, 5)", "translate(0, 8) scale(0.9, 0.9)");
bodyHtml = bodyHtml.replace(regexReplay, chartHtmlString);

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