<?php
namespace app\meditation\model;

use think\Model;

class MeditationModel extends Model
{
    protected $table='meditation';

    public function getDurationByUserId($userID){
        $data = $this->where('user_id',$userID)->column('duration');
        return $data;
    }

    public function getDaysByUserId($userID){
        $data = $this->where('user_id',$userID)->order('date')->column('date(date)');
        return $data;
    }
}