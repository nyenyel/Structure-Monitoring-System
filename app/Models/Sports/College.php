<?php

namespace App\Models\Sports;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class College extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'title',
        'acronym',
        'logo'
    ];
    public function team(): HasMany
    {
        return $this->hasMany(Team::class, 'college_id');
    }
}
