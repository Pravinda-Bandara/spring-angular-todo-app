import {Component, Inject} from '@angular/core';
import {TaskService} from "../../service/task-service";

@Component({
  selector: 'app-form',
  template: `
    <form (ngSubmit)="onSubmit(txt)" action="" class="flex p-2 gap-1 border-b border-gray-700 sticky top-0 bg-[#1E1F22]">
        <input #txt type="text"
               placeholder="Eg. Finish To Do App Design"
        class="border flex-grow p-1 px-2 outline-0
                bg-transparent border-gray-600  caret-sky-500 text-white
                rounded focus:ring-2 hover:border-sky-300 focus:ring-sky-400">
        <button class="border px-2 border-gray-700 font-bold
            hover:shadow-lg hover:shadow-cyan-900 outline-0 focus:ring-1 ring-emerald-600
            bg-gradient-to-r from-indigo-500 to-sky-500 text-slate-100 rounded hover:from-green-600 hover:to-sky-500">
            ADD
        </button>
    </form>
  `,
  styleUrl: './form.component.scss'
})
export class FormComponent {
  constructor(@Inject(TaskService) private taskService: TaskService) {
  }

  async onSubmit(txtElm: HTMLInputElement) {
    if (!txtElm.value.trim()){
      txtElm.focus();
      txtElm.select();
    }else{
      try {
        await this.taskService.createTask(txtElm.value.trim());
        txtElm.value = '';
        txtElm.focus();
      }catch (err){}
    }
  }
}
