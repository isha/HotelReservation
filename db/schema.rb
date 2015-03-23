# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20150323233001) do

  create_table "customers", id: false, force: true do |t|
    t.string   "name"
    t.integer  "phone_number", limit: 8
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "employees", force: true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "room_types", id: false, force: true do |t|
    t.string   "rtype"
    t.integer  "security_deposit"
    t.integer  "daily_rate"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "rooms", id: false, force: true do |t|
    t.integer  "r_number"
    t.string   "r_type"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
