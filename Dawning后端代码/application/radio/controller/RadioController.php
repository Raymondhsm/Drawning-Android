<?php
namespace app\radio\controller;

use app\common\FileRepository;

class RadioController
{
    // public function index()
    // {
        
    // }

    /* 电台主页 */
    public function getProgramDailyRecommended()
    {
        return model('RadioLogic','logic')->getProgramDailyRecommended();
    }

    public function getRadioHomeBanners()
    {
        $data = model('RadioLogic','logic')->getRadioHomeBanners();
        return $data;
    }

     /* 主播电台 */
    public function getRadios()
    {
        return model('RadioLogic','logic')->getRadios();
    }


    /* 电台分类 */
    public function getProgramsByCategory()
    {   
        //由于thinkphp貌似不支持中文路由，所以使用参数
        $category = \input('category');
        return model('RadioLogic','logic')->getProgramsByCategory($category);
    }


    /* 电台详情 */
    public function getRadioAndPrograms($radio_id)
    {
        return model('RadioLogic','logic')->getRadioAndPrograms($radio_id);
    }

    public function addRadioSubscription($radio_id)
    {
        return model('RadioLogic','logic')->addRadioSubscription($radio_id);
    }

    public function cancelRadioSubscription($radio_id)
    {
        return model('RadioLogic','logic')->cancelRadioSubscription($radio_id);
    }

    public function addProgramFav($program_id)
    {
        return model('RadioLogic','logic')->addProgramFav($program_id);
    }

    //下载节目
    public function downloadProgram($program_id)
    {
        // $file_name = \input('file_link');
        return model('RadioLogic','logic')->downloadProgram($program_id);
    }

    public function getDownloadProgramInfo($program_id)
    {
        return model('RadioLogic','logic')->getDownloadProgramInfo($program_id);
    }

    //
}