<?php
namespace app\diary\controller;

use app\common\FileRepository;

class DiaryController
{
    public function getDiary($token){
        return model('DiaryLogic','logic')->getDiary($token);

    }

    public function addDiary($date,$title,$content,$mood,$user_id){
        return model('DiaryLogic','logic')->addDiary();
    }

    public function updateDiary($diary_id,$title,$date,$content,$mood,$user_id){
        return model('DiaryLogic','logic')->updateDiary();
    }

    public function deleteDiary($diary_id){
        return model('DiaryLogic','logic')->deleteDiary($diary_id);
    }
}