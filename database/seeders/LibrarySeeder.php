<?php

namespace Database\Seeders;

use App\Models\Library\LibAward;
use App\Models\Library\LibGameStatus;
use App\Models\Library\LibGender;
use App\Models\Library\LibIncidentStatus;
use App\Models\Library\LibRole;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class LibrarySeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $awards = [
            ['desc' => 'N/A'],
            ['desc' => 'No Award'],
            ['desc' => 'Bronze'],
            ['desc' => 'Silver'],
            ['desc' => 'Gold'],
        ];
        $gameStatus = [
            ['desc' => 'No Schedule'],
            ['desc' => 'Live'],
            ['desc' => 'Finished'],
            ['desc' => 'Delayed'],
            ['desc' => 'Waiting'],
        ];

        $incidentStatus = [
            ['desc' => 'Resolved'],
            ['desc' => 'Pending'],
        ];

        foreach($awards as $award){
            LibAward::create($award);
        }
        foreach($gameStatus as $status){
            LibGameStatus::create($status);
        }

        foreach($incidentStatus as $status){
            LibIncidentStatus::create($status);
        }

    }
}
