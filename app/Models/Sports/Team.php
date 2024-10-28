<?php

namespace App\Models\Sports;

use App\Models\Library\LibAward;
use App\Models\Users\BasicInformation;
use App\Models\Users\Player;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Relations\HasOne;

class Team extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'title',
        'logo',
        'sports_id',
        'college_id',
        'lib_award_id',
        'coach_id',
    ];

    public function sports():BelongsTo
    {
        return $this->belongsTo(Sports::class, 'sports_id');
    }
    public function college():BelongsTo
    {
        return $this->belongsTo(College::class, 'college_id');
    }
    public function award():BelongsTo
    {
        return $this->belongsTo(LibAward::class, 'lib_award_id');
    }
    public function coach():BelongsTo
    {
        return $this->belongsTo(BasicInformation::class, 'coach_id');
    }

    public function firstTeamSched():HasMany
    {
        return $this->hasMany(Schedule::class, 'first_team_id');
    }
    public function secondTeamSched():HasMany
    {
        return $this->hasMany(Schedule::class, 'second_team_id');
    }
    public function winningTeamSched():HasMany
    {
        return $this->hasMany(Schedule::class, 'winner_team_id');
    }
    public function player(): HasMany
    {
        return $this->hasMany(Player::class, 'team_id');
    }
    
    
}
