<?php

namespace App\Models\Library;

use App\Models\Sports\Sports;
use App\Models\Users\Player;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class LibPosition extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'desc',
        'sports_id'
    ];
    public function player(): HasMany
    {
        return $this->hasMany(Player::class, 'position_id');
    }
    public function sports(): BelongsTo
    {
        return $this->belongsTo(Sports::class);
    }
}
