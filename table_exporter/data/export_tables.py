import xlrd
import unicodecsv
import timeit


def main():
    columns = { 'items' :
                    ['_id', 'name', 'jpn_name', 'type', 'sub_type',
                     'rarity', 'carry_capacity', 'buy', 'sell', 'description',
                     'icon_name', 'armor_dupe_name_fix'],
                'combining' :
                    ['_id', 'created_item_id', 'item_1_id', 'item_2_id', 'amount_made_min',
                     'amount_made_max', 'percentage'],
                'armor' : 
                    ['_id','slot','defense','max_defense',
                     'fire_res','thunder_res','dragon_res','water_res','ice_res',
                     'gender','hunter_type','num_slots'],
                'weapons' : 
                    ['_id','wtype','creation_cost','upgrade_cost','attack','max_attack','element','element_attack',
                     'element_2','element_2_attack','awaken_element','awaken_element_attack',
                     'defense','sharpness','affinity','horn_notes','shelling_type',
                     'phial','charges','coatings','recoil','reload_speed','rapid_fire','deviation','ammo',
                     'sharpness_file','num_slots','tree_depth','final'],
                'skill_trees' :
                    ['_id','name','jpn_name'],
                'skills' :
                    ['_id','skill_tree_id','required_skill_tree_points','name','jpn_name','description'],
                'item_to_skill_tree' :
                    ['_id','item_id','skill_tree_id','point_value'],
                'components' :
                    ['_id','created_item_id','component_item_id','quantity','type'],
                'monsters' :
                    ['_id','class','name','trait','icon_name','sort_name'],
                'monster_damage' :
                    ['_id','monster_id','body_part','cut','impact','shot',
                     'fire','water','ice','thunder','dragon','ko'],
                'locations' :
                    ['_id','name','map','jpn_name'],
                'quests' :
                    ['_id','name','goal','hub','type','stars','location_id','time_limit','fee',
                     'reward','hrp','sub_goal','sub_reward','sub_hrp'],
                'arena_quests' :
                    ['_id','name','goal','location_id','reward','num_participants',
                     'time_s','time_a','time_b'],
                'arena_rewards' : 
                    ['_id','arena_id','item_id','percentage','stack_size'],
                'decorations' : 
                    ['_id','num_slots'],
                'gathering' :
                    ['_id','item_id','location_id','area','site','rank','quantity','percentage'],
                'hunting_rewards' :
                    ['_id','item_id','condition','monster_id','rank','stack_size','percentage'],
                'monster_to_arena' :
                    ['_id','monster_id','arena_id'],
                'monster_to_quest' :
                    ['_id','monster_id','quest_id','unstable'],
                'quest_rewards' :
                    ['_id','quest_id','item_id','reward_slot','percentage','stack_size'],
                'trading' :
                    ['_id','location_id','offer_item_id','receive_item_id','percentage'],
                'monster_habitat' :
                    ['_id', 'monster_id','location_id','start_area','move_area','rest_area'],
                'monster_status' :
                    ['_id', 'monster_id','status','initial','increase','max','duration','damage']
              }
              
    wbl = []
    wbl.append(xlrd.open_workbook('Monster Hunter 4U Database.xlsx'))
    wbl.append(xlrd.open_workbook('Monster Hunter 4U Database 2.xlsx'))
    for table, col_list in columns.iteritems():
        sheet_found = False
        sh = ""
        for wb in wbl:
            try:
                sh = wb.sheet_by_name(table)
            except xlrd.biffh.XLRDError:
                #print 'Table ', table, ' not found'
                continue
            sheet_found = True
        if not sheet_found:
            print 'Table ', table, ' not found'
            continue
        your_csv_file = open(table + '.tsv', 'wb')
        wr = unicodecsv.writer(your_csv_file, delimiter='\t', encoding='utf-8')
        header = sh.row_values(0)
        arranged_order = []
        found = True
        first = True
        for col in col_list:
            try:
                idx = header.index(col)
            except ValueError:
                print 'Column ', col, ' in ', table, ' not found'
                found = False
                exit()
            arranged_order.append(idx)
        for rownum in xrange(sh.nrows):
            if first:
                first = False
                continue
            row = sh.row_values(rownum)
            arranged_row = [ clean_row(row[i]) for i in arranged_order]
            wr.writerow(arranged_row)
        your_csv_file.close()

def clean_row(value):
    if isinstance(value, (int, long, float, complex)):
        value = int(value)
    return unicode(value)

if __name__ == "__main__":
    main()
