<?php
namespace app\radio\logic;

use think\Model;
use app\radio\model\ProgramModel;
use app\radio\model\RadioModel;
use app\radio\model\SubscribeRadioModel;
use app\radio\model\FavoriteProgramModel;

use app\common\FileRepository;

class RadioLogic extends Model
{
    //电台主页
    public function getProgramDailyRecommended()
    {   
        return \think\Loader::model('ProgramModel','model')->getProgramsOrderByPlayCount(2);
    }

    public function getRadioHomeBanners()
    {
        return \think\Loader::model('BannerModel','model')->getLatestBanners();
    }

    //节目分类
    public function getProgramsByCategory($category)
    {   
        return \think\Loader::model('ProgramModel','model')->getProgramsByCategory($category);
    }

    public function getProgramCategory()
    {

    }

    //主播电台
    //考虑分段
    public function getRadios()
    {
        return \think\Loader::model('RadioModel','model')->getRadios();
    }

    //电台详情
    public function getRadioAndPrograms($radio_id)
    {
        $data = ['radio_info'=>$this->getRadioInfo($radio_id),
                'programs'=>$this->getRadioPrograms($radio_id),
                'is_subscribe'=>$this->isUserSubscribeRadio($radio_id,\get_user_id(\input('token')))];
        return $data;
    }

    public function getRadioInfo($radio_id)
    {
        return RadioModel::where('radio_id',$radio_id)->field('radio_id,radio_name,cover_link')->find();
    }

    public function isUserSubscribeRadio($radio_id,$user_id)
    {
        return SubscribeRadioModel::get(['radio_id'=>$radio_id,'user_id'=>$user_id])==null?false:true;
    }

    public function addRadioSubscription($radio_id)
    {
        $subscribe = new SubscribeRadioModel();
        $subscribe->data([
            'radio_id' => $radio_id,
            'user_id'  => \get_user_id(\input('token'))
        ]);
        return $subscribe->save()>0?true:false;
    }

    public function cancelRadioSubscription($radio_id)
    {
        $result = SubscribeRadioModel::where(['radio_id'=>$radio_id,'user_id'=> \get_user_id(\input('token'))])->delete();
        return $result>0?true:false;
        // return SubscribeRadioModel::destroy(['radio_id'=>$radio_id,'user_id'=> \get_user_id(\input('token'))]);
    }

    public function getRadioPrograms($radio_id)
    {
        // return \think\Loader::model('ProgramModel','model')->getProgramsByRadioId($radio_id);
        return ProgramModel::where('radio_id',$radio_id)->field('program_id,program_name,upload_time,play_count,duration')->select();
    }

    public function addProgramFav($program_id)
    {
        FavoriteProgramModel::create([
            'user_id'  =>  \get_user_id(\input('token')),
            'program_id' =>  $program_id
        ]);
    }

    //下载节目

    public function downloadProgram($program_id)
    {
        self::addProgramPlayCount($program_id);
        $program_link = ProgramModel::where('program_id',$program_id)->value('program_link');
        return FileRepository::downloadFile($program_link);
    }

    public function getDownloadProgramInfo($program_id)
    {
        return \think\Loader::model('ProgramModel','model')->getDownloadProgramInfo($program_id);
    }

    public function addProgramPlayCount($program_id){
       $program =  ProgramModel::get($program_id);
       $program->play_count = $program->play_count+1;
       $program->save();
    }

    public function playProgram()
    {

    }
}