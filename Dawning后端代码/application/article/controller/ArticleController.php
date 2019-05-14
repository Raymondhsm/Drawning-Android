<?php
namespace app\article\controller;

class ArticleController
{


    public function getArticleRecommended(){
        return model('ArticleLogic','logic')->getArticalDailyRecommended();
    }

    public function getArticlesByCategory($category)
    {
        $category = \input('category');
        return model('ArticleLogic','logic')->getArticlesByCategory($category);
    }

    public function getArticleHomeBanners()
    {
        $data = model('ArticleLogic','logic')->getArticleHomeBanners();
        return $data;
    }

    public function publishArticle()
    {
        return model('ArticleLogic','logic')->publishArticle();
    }

    public function getArticleDetail($article_id)
    {
        return model('ArticleLogic','logic')->getArticleDetail($article_id);
    }

}