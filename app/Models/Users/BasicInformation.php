<?php

namespace App\Models\Users;

use App\Models\Library\LibGender;
use App\Models\Sports\Team;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasOne;

class BasicInformation extends Model
{
    use HasFactory;
    protected $guarded = ['id'];
    protected $fillable = [
        'first_name',
        'middle_name',
        'last_name',
        'phone_no',
        'lib_gender_id',
    ];
    public function gender() : BelongsTo
    {  
        return $this->belongsTo(LibGender::class, 'lib_gender_id');
    }
    public function coach(): HasOne
    {  
        return $this->hasOne(Team::class, 'coach_id');
    }
    public function player(): HasOne
    {  
        return $this->hasOne(Player::class, 'basic_information_id');
    }
    public function user() : HasOne
    {  
        return $this->hasOne(User::class, 'basic_information_id');
    }

}
