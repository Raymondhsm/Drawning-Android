<?php
namespace app\user\logic;

use app\meditation\model\MeditationModel;
use app\user\model\UserLoginModel;
use app\diary\model\DiaryModel;
use app\diary\model\MoodModel;
use think\Model;
use think\Db;

class ReportLogic
{
    public static $userID;

    public function getMeditationTotalTime(){
        $meditation= new MeditationModel();
        $data = $meditation->getDurationByUserId(ReportLogic::$userID);

        $sum=0;
        for($i=0;$i<count($data);$i++){
            $sum+=$data[$i];
        }

        return $sum;
    }

    public function getMeditationTimes(){
        $meditation= new MeditationModel();
        $data = $meditation->getDurationByUserId(ReportLogic::$userID);
        return count($data);
    }

    public function getMeditationAvgTime(){
        $totalTime=$this->getMeditationTotalTime();
        $times=$this->getMeditationTimes();

        $avg=round($totalTime/$times,1);
        return $avg;
    }

    public function getFrequency(){
        $totalTime=$this->getMeditationTimes();
        $meditation= new MeditationModel();
        $data = $meditation->getDaysByUserId(ReportLogic::$userID);

        $earlyDay=strtotime($data[0]);
        $lateDay=strtotime($data[count($data)-1]);

        $days=($lateDay-$earlyDay)/60/60/24+1;

        return round($days/$totalTime,1);
    }
    
    public function getWeekMood(){
        $diary=new DiaryModel();

        $user_id=ReportLogic::$userID;
        $mix=$diary->join('mood','diary.mood_id=mood.mood_id');
        $data=$mix->where("user_id=$user_id")->field('date(create_date) as date,mood_level')->order('create_date desc')->limit('7')->select();
        return $data;
    }

    public function getAvgWeekMood(){
        $diary=new DiaryModel();

        $user_id=ReportLogic::$userID;
        $mix=$diary->join('mood','diary.mood_id=mood.mood_id');
        $data=$mix->where("user_id=$user_id")->field('avg(mood_level) as avgMood')->select();
        return $data;
    }
}