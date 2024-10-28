<?php

namespace App\Models\Users;

use App\Models\Library\LibPlayerStatus;
use App\Models\Library\LibPosition;
use App\Models\Sports\Team;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasOne;

class Player extends Model
{
    use HasFactory;

    protected $guarded = ['id'];
    protected $fillable = [
        'student_no',
        'team_id',
        'position_id',
        'basic_information_id',
        'lib_player_status_id',
    ];
    public function team() : BelongsTo
    {
        return $this->belongsTo(Team::class, 'team_id');
    }
    public function position():BelongsTo
    {
        return $this->belongsTo(LibPosition::class, 'position_id');
    }
    public function basicInformation() : BelongsTo
    {
        return $this->belongsTo(BasicInformation::class, 'basic_information_id');
    }
    public function status () : BelongsTo{
        return $this->belongsTo(LibPlayerStatus::class, 'lib_player_status_id');
    }

}
