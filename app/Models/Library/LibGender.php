<?php

namespace App\Models\Library;

use App\Models\Users\BasicInformation;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;

class LibGender extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'desc'
    ];
    public function basicInformation(): HasMany
    {
        return $this->hasMany(BasicInformation::class, 'lib_gender_id');
    }
}
