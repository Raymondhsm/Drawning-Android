<?php
namespace app\diary\logic;

use think\Model;
use app\diary\model\DiaryModel;

class DiaryLogic
{
    public function getDiary($token)
    {
        return \think\Loader::model("DiaryModel","model")->getDiary($token);
    }

    public function addDiary($title,$date,$content,$mood,$user_id){
        $diary = new DiaryModel;
        $diary->title=$title;
        $diary->date=$date;
        $diary->content=$content;
        $diary->mood=mood;
        $diary->user_id=$user_id;
        $diary->save();
        return true;
    }

    public function updateDiary($diary_id,$title,$date,$content,$mood,$user_id){
        $diarymodel = new DiaryModel;
        $diary = $diarymodel->where("diary_id=$diary_id")->find();
        $diary->title=$title;
        $diary->date=$date;
        $diary->content=$content;
        $diary->mood=mood;
        $diary->user_id=$user_id;
        $diary->save();
        return true;
    }

    public function deleteDiary($diary_id){
        $diarymodel = new DiaryModel;
        $diary = $diarymodel->where("diary_id=$diary_id")->find();
        $diary->delete();
    }
}