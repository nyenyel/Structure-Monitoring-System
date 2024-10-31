<?php

namespace App\Models\Sports;

use App\Models\Library\LibGameStatus;
use App\Models\Others\Banner;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Schedule extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'date',
        'time',
        'match_no',
        'match_round',
        'next_match_id',
        'first_team_id',
        'second_team_id',
        'winner_team_id',
        'sports_id',
        'lib_game_statuses_id',
        'referee_full_name',
        'first_team_score',
        'second_team_score',
        'banner'
    ];
    public function firstTeam(): BelongsTo
    {
        return $this->belongsTo(Team::class, 'first_team_id');
    }
    public function secondTeam(): BelongsTo
    {
        return $this->belongsTo(Team::class, 'second_team_id');
    }
    public function winningTeam(): BelongsTo
    {   
        return $this->belongsTo(Team::class, 'winner_team_id');
    }
    public function sports(): BelongsTo
    {
        return $this->belongsTo(Sports::class, 'sports_id');
    }
    public function gameStatus(): BelongsTo
    {
        return $this->belongsTo(LibGameStatus::class, 'lib_game_statuses_id');
    }

    public function nextMatch(): BelongsTo
    {
        return $this->belongsTo(Schedule::class, 'next_match_id');
    }
    public function banner(): BelongsTo 
    {
        return $this->belongsTo(Banner::class, 'banner');
    }
}
