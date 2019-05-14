<?php
namespace app\article\model;

use think\Model;
use think\Db;

class ArticleModel extends Model
{

    protected $table = 'article';

    public function getArticleOrderByPlayCount($limit)
    {
        $data = Db::table(['article'])->field('article_id , article_name , cover_link')
            ->order('read_count desc')->limit($limit)->select();
        return $data;
    }

    public function getArticlesByCategory($category)
    {
        if($category=="全部")
            $data = Db::table('article p')
            ->field('article_id , article_name , cover_link')
            ->select();
        else 
            $data = Db::table('article p')
            ->where(['category'=>$category])
            ->field('article_id , article_name , cover_link')
            ->select();
        return $data;
    }

}