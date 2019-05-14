<?php
namespace app\user\logic;

use app\user\model\UserLoginModel;
use app\radio\model\ProgramModel;
use app\radio\model\RadioModel;
use think\Model;
use think\Db;

class MixLogic
{
    public static $userID;

    public function getProgramMix(){
        $program = new ProgramModel();
        $result = $program->join("favorite_program","program.program_id=favorite_program.program_id");
        $result = $result->join("radio",'program.radio_id=radio.radio_id');
        $user_id=MixLogic::$userID;
        $data = $result->where("user_id=$user_id")->field('program.program_id,program_name,radio_name,cover_link')->select();
        return $data;
    }

    public function getMyRadio(){
        $radio = new RadioModel();
        $result = $radio->join("subscribe_radio","radio.radio_id=subscribe_radio.radio_id");
        $user_id=MixLogic::$userID;
        //$data = $result->field('radio.radio_id','radio_name','cover_link','count(radio.radio_id)')->group('radio_id')->where("user_id=$user_id")-select();
        $data=Db::query("select radio.radio_id,radio_name,cover_link,subscribe_count from radio natural join subscribe_radio NATURAL join (select radio_id,COUNT(user_id) as subscribe_count from subscribe_radio GROUP BY radio_id) as num where user_id=$user_id;");
        return $data;
    }
}