class RoomTypesController < ApplicationController
  def index
    @room_types = RoomType.all
  end

  def new
    @room_type = RoomType.new
  end

  def create
    @room_type = RoomType.new(room_type_params)
 
    if @room_type.save
      redirect_to @room_type
    else
      render 'new'
    end
  end

  def show
    @room_type = RoomType.find(params[:rtype])
  end

  def edit
    @room_type = RoomType.find(params[:rtype])
  end

  def update
    @room_type = RoomType.find(params[:rtype])
 
    if @room_type.update(room_type_params)
      redirect_to @room_type
    else
      render 'edit'
    end
  end

  def destroy
    @room_type = RoomType.find(params[:rtype])
    @room_type.destroy

    redirect_to room_types_path
  end

  private
  def room_type_params
    params.require(:room_type).permit(:rtype, :daily_rate, :security_deposit)
  end
end
