export class Group {
    constructor(
        public name: string = '',
        public id: number = 0,
        public accounts: Account[] = []
    ) { }
}