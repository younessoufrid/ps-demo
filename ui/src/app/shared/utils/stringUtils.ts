export function camelize(str) {
  return str.replace(/(?:^\w|[A-Z]|\b\w)/g, (word, index) => {
    return index === 0 ? word.toUpperCase() : word.toLowerCase();
  });
}
export function isString(x: any) {
  return x != null && typeof x === 'string';
}
