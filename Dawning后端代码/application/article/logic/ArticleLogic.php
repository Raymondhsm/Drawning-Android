<?php
namespace app\article\logic;

use think\Model;

use app\article\model\ArticleModel;
use app\article\model\SubscribeArticleModel;

class ArticleLogic extends Model
{
    public function getArticleDailyRecommended(){
        return \think\Loader::model('ArticleModel','model')->getArticleOrderByPlayCount(3);
    }

    //文章分类
    public function getArticlesByCategory($category)
    {   
        return \think\Loader::model('ArticleModel','model')->getArticlesByCategory($category);
    }

    public function getArticleHomeBanners()
    {
        return \think\Loader::model('BannerModel','model')->getLatestBanners();
    }

    public function publishArticle()
    {
        ArticleModel::create([
            'article_name' => input('article_name'),
            'article_content' => input('article_content'),
            'article_category' => input('article_category'),
            'user_id' => input('user_id')
        ]);
    }

    public function getArticleDetail($article_id)
    {
        return model('ArticleLogic','logic')->getArticleDetail($article_id);
    }
}