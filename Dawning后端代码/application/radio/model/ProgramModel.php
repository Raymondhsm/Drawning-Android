<?php
namespace app\radio\model;

use think\Model;
use think\Db;

class ProgramModel extends Model
{

    protected $table = 'program';

    public function getProgramsOrderByPlayCount($limit)
    {
        $data = Db::table(['program','radio'])->field('program_id , program_name , radio.radio_name , radio.cover_link')
            ->order('play_count desc')->limit(2)->select();
        // $data = $this->field('program_id,program_name')->order('play_count','desc')->limit(2)->select();
        // $data = $this->get(1)->radio;
        // \dump($data);
        return $data;
    }

    public function getProgramsByCategory($category)
    {

        if($category=="全部")
            $data = Db::table('program p')
            ->join('radio r','r.radio_id=p.radio_id','INNER')
            ->field('program_id , program_name , radio_name , cover_link')
            ->select();
        else 
            $data = Db::table('program p')
            ->join('radio r','r.radio_id=p.radio_id','INNER')
            ->where(['category'=>$category])
            ->field('program_id , program_name , radio_name , cover_link')
            ->select();
        return $data;
    }

    public function getProgramsByRadioId($radio_id)
    {
        return self::where('radio_id',$radio_id)
                ->field('program_id,program_name,upload_time,play_count,duration')
                ->order('upload_time desc')
                ->select();
    }

    public function getDownloadProgramInfo($program_id)
    {
        return Db::table('program p')
                ->join('radio r','r.radio_id=p.radio_id','INNER')
                ->where(['program_id'=>$program_id])
                ->field('program_id , program_name , p.radio_id , radio_name')
                ->find();
    }


    //方法名叫radio或者radioModel都没关系
    public function radio()
    {
        return $this->belongsTo('RadioModel','radio_id');
    }
}