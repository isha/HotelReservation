class ChangeTypeToRtype < ActiveRecord::Migration
  def change
    rename_column :room_types, :type, :rtype
  end
end
