<?php
namespace app\user\controller;

use app\user\logic\ReportLogic;
use app\user\logic\MixLogic;
use app\user\logic\MyArticleLogic;
use app\user\logic\LoginLogic;
use app\user\model\UserLoginModel;
use think\Request;

class UserController
{
    public function meditationReport($token)
    {
        $user=new UserLoginModel();
        $report=new ReportLogic();
        ReportLogic::$userID=$user->getUserIdByToken($token)[0];

        $avg=$report->getMeditationAvgTime();
        $times=$report->getMeditationTimes();
        $totalTime=$report->getMeditationTotalTime();
        $frequency=$report->getFrequency();
        
        $data=['avgTime'=>$avg,
                'times'=>$times,
                'totalTime'=>$totalTime,
                'frequency'=>$frequency
        ];

        return $data;
    }

    public function moodReport($token)
    {
        $user=new UserLoginModel();
        $report=new ReportLogic();
        ReportLogic::$userID=$user->getUserIdByToken($token)[0];

        $weekMood=$report->getWeekMood();
        $avgMood=$report->getAvgWeekMood();

        $data=[
            $weekMood,
            $avgMood
        ];
        return $data;
    }

    public function getProgramMix($token){
        $user=new UserLoginModel();
        $mix=new MixLogic();
        MixLogic::$userID=$user->getUserIdByToken($token)[0];


        return $mix->getProgramMix();
    }


    public function getMyRadio($token){
        $user=new UserLoginModel();
        $mix=new MixLogic();
        MixLogic::$userID=$user->getUserIdByToken($token)[0];

        return $mix->getMyRadio();
    }


    public function Login(){
        $login = new LoginLogic();
        $account = input('post.account');
        $psd = input('post.psd');
        return $login->Login($account,$psd);
    }


    public function Register(){
        $login = new LoginLogic();
        $account = input('post.account');
        $psd = input('post.psd');
        $name = input('post.name');
        $sex = input('post.sex');
        $age = input('post.age');
        $avatar = input('post.avatar_link');

        return $login->Register($account,$psd,$name,$sex,$age,$avatar);
    }
}
